package aghost7.bebop.event

import java.awt.event.{WindowListener, WindowEvent, WindowAdapter}
import java.awt.Window


sealed trait BWindowEvent {
	val ev: WindowEvent
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
	
	def onWindowActivated(func: WindowEvent => Unit): WindowListener = {
		val l = new WindowAdapter(){
			override def windowActivated(ev: WindowEvent): Unit = func(ev)
		}
		self.addWindowFocusListener(l)
		l
	}
		
		
	def onWindowClosed(func: WindowEvent => Unit): WindowListener = {
		val l = new WindowAdapter(){
			override def windowClosed(ev: WindowEvent): Unit = func(ev)
		}
		self.addWindowFocusListener(l)
		l
	}
		
		
	def onWindowClosing(func: WindowEvent => Unit): WindowListener = {
		val l = new WindowAdapter(){
			override def windowClosing(ev: WindowEvent): Unit = func(ev)
		}
		self.addWindowFocusListener(l)
		l
	}
		
		
	def onWindowDeactivated(func: WindowEvent => Unit): WindowListener = {
		val l = new WindowAdapter(){
			override def windowDeactivated(ev: WindowEvent): Unit = func(ev)
		}
		self.addWindowFocusListener(l)
		l
	}
		
		
	def onWindowDeiconified(func: WindowEvent => Unit): WindowListener = {
		val l = new WindowAdapter(){
			override def windowDeiconified(ev: WindowEvent): Unit = func(ev)
		}
		self.addWindowFocusListener(l)
		l
	}
		
		
	def onWindowIconified(func: WindowEvent => Unit): WindowListener = {
		val l = new WindowAdapter(){
			override def windowIconified(ev: WindowEvent): Unit = func(ev)
		}
		self.addWindowFocusListener(l)
		l
	}
		
		
	def onWindowOpened(func: WindowEvent => Unit): WindowListener = {
		val l = new WindowAdapter(){
			override def windowOpened(ev: WindowEvent): Unit = func(ev)
		}
		self.addWindowFocusListener(l)
		l
	}
		
		
	def addBWindowListener(part: PartialFunction[BWindowEvent, Unit]) : WindowListener = {
		val func = part.lift
		val l = new WindowListener() {
			
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
		}
		self.addWindowListener(l)
		l
	}
}