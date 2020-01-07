#/bin/bash

if [[ $1 != "androidTest" && $1 != "main" && $1 != "test" ]]
then
  echo "usage: $0 <main | androidTest | test>"
  exit 1
fi

(find app/src/$1/java/com/rrm/learnification/ | grep "[.]java" | xargs -n 1 wc -l | awk '{$1=$1};1' | cut -d ' ' -f1 | tr "\012" "+" ; echo "0") | bc
