if [[ $# != "1" ]]
then
  echo "Usage: $0 <output file>"
  exit 1
fi

adb shell uiautomator dump
adb pull /sdcard/window_dump.xml
mv window_dump.xml $1
