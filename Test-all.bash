#!/usr/bin/env bash
#
# Sample usage:
#
#   HOST=localhost PORT=7000 ./test-em-all.bash
#
# When not using docker
#: ${HOST=localhost}
#: ${PORT=7000}

#When using docker
: ${HOST=localhost}
: ${PORT=8080}
: ${PORTUSER=8081}

function assertCurl() {

  local expectedHttpCode=$1
  local curlCmd="$2 -w \"%{http_code}\""
  local result=$(eval $curlCmd)
  local httpCode="${result:(-3)}"
  RESPONSE='' && (( ${#result} > 3 )) && RESPONSE="${result%???}"

  if [ "$httpCode" = "$expectedHttpCode" ]
  then
    if [ "$httpCode" = "200" ]
    then
      echo "Test OK (HTTP Code: $httpCode)"
    else
      echo "Test OK (HTTP Code: $httpCode, $RESPONSE)"
    fi
  else
      echo  "Test FAILED, EXPECTED HTTP Code: $expectedHttpCode, GOT: $httpCode, WILL ABORT!"
      echo  "- Failing command: $curlCmd"
      echo  "- Response Body: $RESPONSE"
      exit 1
  fi
}

function assertEqual() {

  local expected=$1
  local actual=$2

  if [ "$actual" = "$expected" ]
  then
    echo "Test OK (actual value: $actual)"
  else
    echo "Test FAILED, EXPECTED VALUE: $expected, ACTUAL VALUE: $actual, WILL ABORT"
    exit 1
  fi
}

function testUrl() {
  url=$@
  if curl $url -ks -f -o /dev/null
  then
    echo "Ok"
    return 0
    else
      echo -n "not yet"
      return 1
  fi;
}

function setupTestdata() {
  body=\
'{"movieId":1,"name":"movie 1","director": "director 1","theatreReleaseDate":"theatreReleaseDate 1"}'
    recreateComposite 1 "$body"
    body=\
'{"movieId":77,"name":"movie 77","director":"director 77","theatreReleaseDate":"theatreReleaseDate 77", "review":[
    {"reviewId":1,"author":"author 1","subject":"subject 1","content":"content 1"},
    {"reviewId":2,"author":"author 2","subject":"subject 2","content":"content 2"},
    {"reviewId":3,"author":"author 3","subject":"subject 3","content":"content 3"}
]}'
    recreateComposite 77 "$body"
  body=\
'{"userId":2,"name":"name 2","familyName": "familyName 2","gender":"gender 2","phoneNumber":"phoneNumber 2","address":"address 2"}'
    recreateUser 2 "$body"
}


function recreateComposite() {

  local movieId=$1
  local composite=$2

  assertCurl 200 "curl -X DELETE http://$HOST:$PORT/movie-composite/${movieId} -s"
  curl -X POST http://$HOST:$PORT/movie-composite -H "Content-Type:
  application/json" --data "$composite"
}

function recreateUser() {

  local userId=$1
  local composite=$2

  assertCurl 200 "curl -X DELETE http://$HOST:$PORTUSER/user/${userId} -s"
  curl -X POST http://$HOST:$PORTUSER/user -H "Content-Type:
  application/json" --data "$composite"
}

function waitForService() {
  url=$@
  echo -n "Wait for: $url... "
  n=0
  until testUrl $url
  do
    n=$((n + 1))
    if [[ $n == 100 ]]
    then
      echo " Give up"
      exit 1
      else
        sleep 6
        echo -n ", retry #$n "
    fi
  done
}

set -e

echo "HOST=${HOST}"
echo "PORTCOMPOSITE=${PORT}"
echo "PORTUSER=${PORTUSER}"

if [[ $@ == *"start"* ]]
then
  echo "Restarting the test
  environment..."
  echo "$ docker-compose down"
  docker-compose down
  echo "$ docker-compose up -d"
  docker-compose up -d
fi

waitForService curl -X DELETE http://$HOST:$PORT/movie-composite/13
waitForService curl -X DELETE http://$HOST:$PORTUSER/user/55

setupTestdata

# Verify that no reviews are returned for movieId 1
assertCurl 200 "curl http://$HOST:$PORT/movie-composite/1 -s"
assertEqual 1 $(echo $RESPONSE | jq .movieId)

# Verify that a normal request works, expect three recommendations and three reviews
assertCurl 200 "curl http://$HOST:$PORT/movie-composite/77 -s"
assertEqual 77 $(echo $RESPONSE | jq .movieId)
assertEqual 3 $(echo $RESPONSE | jq ".review | length")

# Verify that a 404 (Not Found) error is returned for a non existing movieId (13)
assertCurl 404 "curl http://$HOST:$PORT/movie-composite/13 -s"

# Verify that a 422 (Unprocessable Entity) error is returned for a movieId that is out of range (-1)
assertCurl 422 "curl http://$HOST:$PORT/movie-composite/-1 -s"
assertEqual "\"invalid movieId: -1\"" "$(echo $RESPONSE | jq .message)"

# Verify that a 400 (Bad Request) error error is returned for a movieId that is not a number, i.e. invalid format
assertCurl 400 "curl http://$HOST:$PORT/movie-composite/notinteger -s"
assertEqual "\"Type mismatch.\"" "$(echo $RESPONSE | jq .message)"

# Verify that a 400 (Bad Request) error error is returned for a movieId that is not a number, i.e. invalid format
assertCurl 422 "curl http://$HOST:$PORT/movie-composite/155 -s"
assertEqual "\"The movieId cannot exceed 100 :155\"" "$(echo $RESPONSE | jq .message)"

#USER TESTS

# Verify that no reviews are returned for movieId 2$
assertCurl 200 "curl http://$HOST:$PORTUSER/user/2 -s"
assertEqual 2 $(echo $RESPONSE | jq .userId)

# Verify that a 404 (Not Found) error is returned for a non existing userId (55)
assertCurl 404 "curl http://$HOST:$PORTUSER/user/55 -s"

# Verify that a 422 (Unprocessable Entity) error is returned for a movieId that is out of range (-1)
assertCurl 422 "curl http://$HOST:$PORTUSER/user/-1 -s"
assertEqual "\"Invalid userId: -1\"" "$(echo $RESPONSE | jq .message)"

# Verify that a 400 (Bad Request) error error is returned for a movieId that is not a number, i.e. invalid format
assertCurl 400 "curl http://$HOST:$PORTUSER/user/notinteger -s"
assertEqual "\"Type mismatch.\"" "$(echo $RESPONSE | jq .message)"

if [[ $@ == *"stop"* ]]
then
  echo "We are done, stopping the test environment..."
  echo "$ docker-compose down"
  docker-compose down
fi