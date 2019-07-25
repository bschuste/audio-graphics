# audio-graphics

This is a FX graphics application which reads timestamps data from two files produced by an audio receiver and an audio sender and displays the retrieved values on a graphics chart.

It generates a file holding all log lines matching the selection criteria which is the keyword **pts**.

The user can interact with the graphics by clicking:

 - Anywhere on the window to toggle between a full view or a narrowed view
 - On one of the buttons to shift **left** or **right**
 - One the **dot** button to toggle between a dot-marked view or not

To build and test the application, import an IntelliJ project from the files.
As configuration, choose Application and then Main as the main class.
