ClassFinder
===========

Java Class Finder tool to search .jar and .zip files for Java classes.

ClassFinder does provide a CLI as well as a GUI.
When no parameters are provided ClassFinder will try to instantiate a GUI on an X11 terminal.
If this isn't successful, either because $DISPLAY hasn't been exported appropriately or
be cause no X11 terminal exists, the program will automatically print its CLI parameters and exit:

Usage: java -jar ClassFinder.jar|com.optit.ClassFinder -d [directory] -c [classname] -m -v -help|-h|--help|-?

[-d]			The directory to search in
[-c]			The classname to search for
[-m]			Match case
[-r]			Recursive search (search sub directories)
[-v]			Enables verbose output
[-help|--help|-h|-?]	Display this help

The directory specified will be searched recursviely.
The class name can either just be the class name (e.g. String) or the fully qualified name (e.g. java.lang.String)

Good hunting!
