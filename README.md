eXprono

The Basics
=======
eXprono is a two part interface between a computer and an Arduino.

### The GUI:
- The gui is designed for intuitive interaction with the arduino.  You can choose which arduino you are using and which COM port the board is attached to.  In the board ui you can click on pins, manipulate their values, and change pin modes.
- Clicking on the graph checkbox will open up a graph window that plots the values of selected pins in real time.  Random colors are assigned to each pin to distinguish them.
- You can also open up multiple gui's to interface with multiple boards.

### The API:
- This is where the bulk of the functionality of this interface.  The api allows you to write programs in Java that can manipulate pins on an arduino.  Essentially, the arduino now is as powerful and has the same resources (internet, other peripheral devices) as the computer controlling it.

The jSSC library is included for ease of use.  We do not own the code in jSSC.

This idea was developed at the Kent State "Kent Hack Enough" Hackathon by Anno van den Akker, David Lance, Morgan McMahon, Ross O'Hagen.
We are all students at Case Western Reserve University students with expected graduation in May of 2018.
