package aghost7.bebop.event

import java.awt.event.{WindowListener, WindowEvent, WindowAdapter}
import java.awt.Window


sealed trait BWindowEvent {
	def ev: WindowEvent
}

case class WindowClosed(ev: WindowEvent) extends BWindowEvent
case class WindowActivated(ev: WindowEvent) extends BWindowEvent
case class WindowClosing(ev: WindowEvent) extends BWindowEvent
case class WindowDeactivated(ev: WindowEvent) extends BWindowEvent
case class WindowDeiconified(ev: WindowEvent) extends BWindowEvent
case class WindowIconified(ev: WindowEvent) extends BWindowEvent
case class WindowOpened(ev: WindowEvent) extends BWindowEvent

trait WindowListenerExtension {
	val self: Window
	
	def onWindowActivated(func: WindowEvent => Unit): Unit =
		self.addWindowFocusListener(new WindowAdapter(){
			override def windowActivated(ev: WindowEvent): Unit = func(ev)
		})
		
	def onWindowClosed(func: WindowEvent => Unit): Unit =
		self.addWindowFocusListener(new WindowAdapter(){
			override def windowClosed(ev: WindowEvent): Unit = func(ev)
		})
		
	def onWindowClosing(func: WindowEvent => Unit): Unit =
		self.addWindowFocusListener(new WindowAdapter(){
			override def windowClosing(ev: WindowEvent): Unit = func(ev)
		})
		
	def onWindowDeactivated(func: WindowEvent => Unit): Unit =
		self.addWindowFocusListener(new WindowAdapter(){
			override def windowDeactivated(ev: WindowEvent): Unit = func(ev)
		})
		
	def onWindowDeiconified(func: WindowEvent => Unit): Unit =
		self.addWindowFocusListener(new WindowAdapter(){
			override def windowDeiconified(ev: WindowEvent): Unit = func(ev)
		})
		
	def onWindowIconified(func: WindowEvent => Unit): Unit =
		self.addWindowFocusListener(new WindowAdapter(){
			override def windowIconified(ev: WindowEvent): Unit = func(ev)
		})
		
	def onWindowOpened(func: WindowEvent => Unit): Unit =
		self.addWindowFocusListener(new WindowAdapter(){
			override def windowOpened(ev: WindowEvent): Unit = func(ev)
		})
		
	def addBWindowListener(part: PartialFunction[BWindowEvent, Unit]) : Unit = {
		val func = part.lift
		self.addWindowListener(new WindowListener() {
			
			def windowActivated(ev: WindowEvent): Unit = 
				func(WindowActivated(ev))
				
			def windowClosed(ev: WindowEvent): Unit = 
				func(WindowClosed(ev))
				
			def windowClosing(ev: WindowEvent): Unit = 
				func(WindowClosing(ev))
			
			def windowDeactivated(ev: WindowEvent): Unit = 
				func(WindowDeactivated(ev))
			
			def windowDeiconified(ev: WindowEvent): Unit = 
				func(WindowDeiconified(ev))
			
			def windowIconified(ev: WindowEvent): Unit = 
				func(WindowIconified(ev))
				
			def windowOpened(ev: WindowEvent): Unit =
				func(WindowOpened(ev))
		})
	}
}