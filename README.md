# Assignment 2
## Module: CSU33031

Code for Assignment 2: **Flow Forwarding**


## Basic Forward Table (at current stage)

   Container    |  Port Number
--------------- | -------------
ClientOne       |    50000
ForwarderOne    |    50001
ForwarderTwo    |    50002
ClientTwo       |    50003
ForwarderThree  |    50004
ClientThree     |    50005


## TODO

* Add routing tables
* ~~Add user ability to request to send to specific client~~ (kind of)
    * Details of where it's going lie in header?
* Add controller
* Send message to node when it's not wanted
* Send acknowledgements

* Error handling to be done with max amounts in client one *