package aghost7.bebop.event

import java.awt.Window

import java.awt.event.{WindowStateListener, WindowEvent, WindowAdapter}

trait WindowStateListenerExtension {
	val self : Window
	
	def onWindowStateChanged(func: WindowEvent => Unit): Unit =
		self.addWindowFocusListener(new WindowAdapter(){
			override def windowStateChanged(ev: WindowEvent): Unit = func(ev)
		})
		
	def addBWindowStateListener(callback: WindowEvent => Unit): Unit = {
		self.addWindowStateListener(new WindowStateListener(){
			def windowStateChanged(ev: WindowEvent): Unit = callback(ev)
		})
	}
}