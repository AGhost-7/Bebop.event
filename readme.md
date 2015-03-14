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

Some multi-threading utilities are included:
```scala
import aghost7.bebop.event.BConcurrent
import javax.swing.JButton
	
val btn = new JButton("Ok")

// When the scheduler is called for the first time, it will start an executor 
// service and a scheduling thread. This method is more efficient to call
// multiple times compared to he delayCommandOnce.
BConcurrent.delayCommand(1000) {
	// there is invokeAndWait as well
	BConcurrent.invokeLater {
		btn.setName("Not Ok!")
		btn.setEnabled(false)
	}
}

// Code above can be compressed to the following:
import BConcurrent._
invokeLater(1000){
	btn.setName("Not Ok!")
	btn.setEnabled(false)
}

// delayCommandOnce doesn't "recycle" the thread, and instead spawns a new
// thread every time. In other words, this can be useful for blocking delayed 
// calls, etc.
delayCommandOnce(1000)(greetLoop)

def greetLoop: Unit = {
	println("hello")
	Thread.sleep(1000)
	greetLoop
}

// javax.swing.Timer spawner
import java.awt.event.ActionEvent

var count = 0
val loop = looping(100) { ev: ActionEvent =>
	println("loop " + count)
	count += 1
}

delayCommand(1000) { loop.stop }
```

