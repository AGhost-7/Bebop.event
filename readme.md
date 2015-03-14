This is a simple library which extends swing and awt event handling to use anonymous
functions and partial functions instead of interfaces. Code is modular thanks to duck
typing and implicit classes.

One of the reasons behind this library is that when I started learning Scala I looked
into the swing library part of the scala standard at the time. It was a bit too 
overwhelming for me at first. I figured that a set of library modules which allow you 
to gradually "tune the Java out of your Scala code" would make learning the language
more rewarding at the early stages.

The general rule for this module is that any interface which defines one method is
translated to an anonymous function:

```scala
import java.awt.event.ActionEvent
import javax.swing.JButton

import aghost7.bebop.event.implicits._

val btn = new JButton("Greet")
// all "add" methods have a B added since implicit classes don't
// handle overloading very well.
btn.addBActionListener { ev: ActionEvent => println("hello!") }
```

On the other hand, interfaces which have more than one method are partial functions.
You can instead use case statements with what would normally be the method name in 
pascal case, like so:

```scala
import java.awt.event.{WindowListener, WindowEvent}
import javax.swing.JFrame

import aghost7.bebop.event._
import aghost7.bebop.event.implicits._

val frame = new JFrame("Greetings from AGhost")
frame.addBWindowListener {
	case WindowOpened(ev: WindowEvent) => println("hello!")
	case b @ (WindowDeactivated(_) | WindowClosed(_)) =>
		// you can still access the event object from the event wrapper
		// since its defined in the interface.
		val ev: WindowEvent = b.ev
	case WindowDeiconified(ev) => println("??")
}
```

There's also the 'on' line of methods:
```scala
import java.awt.event.ComponentEvent
import javax.swing.JButton
import aghost7.bebop.event.implicits._

val btn = new JButton("Greet")
btn.onComponentResized { ev: ComponentEvent =>
	println("Resized")
}
btn.onMouseExited { _ => 
	println("Not going to click me huh?") 
}
```

The methods return the listener instance so you can unregister the listener if you 
ever need to:
```scala
import javax.swing.{JButton, JTextArea}
import aghost7.bebop.event.implicits._

val txt = new JTextArea()

val listen = txt.onKeyPressed { ev => 
	println("key pressed") 
}
txt.removeKeyListener(listen)
```


