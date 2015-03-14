package aghost7.bebop.event

import java.awt.Window

import java.awt.event.{WindowStateListener, WindowEvent, WindowAdapter}

trait WindowStateListenerExtension {
	val self : Window
	
	def onWindowStateChanged(func: WindowEvent => Unit): WindowStateListener = {
		val l = new WindowAdapter(){
			override def windowStateChanged(ev: WindowEvent): Unit = func(ev)
		}
		self.addWindowFocusListener(l)
		l
	}
		
		
	def addBWindowStateListener(callback: WindowEvent => Unit): WindowStateListener = {
		val l = new WindowStateListener(){
			def windowStateChanged(ev: WindowEvent): Unit = callback(ev)
		}
		self.addWindowStateListener(l)
		l
	}
}