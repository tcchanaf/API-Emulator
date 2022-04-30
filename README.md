# API-Emulator

This project is developed for API Testing, to Test API endpoint server and client side response
  - can produce fake requests or responses to test the developping project case handling

  - receive multi-part API Request 
      - save the file and json parsing
  - configure the fake response which depends on the request json dynamically using csv
      - define getters in dummy_message_map.csv to get the value of the API request message for determining which response from the emulator
      - e.g. file content: can response with a octet-stream or json/application by configuration depends on the request message
  - configure the fake request actively sending out after response a specific request
      - define the request json in dummy_message_map.csv
      - e.g. when the emulator receive a request, if the request is passed the determination(configured using the csv), actively send out a request after 10 seconds
