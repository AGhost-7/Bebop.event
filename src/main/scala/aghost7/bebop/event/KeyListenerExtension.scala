package aghost7.bebop.event

import java.awt.Component
import java.awt.event.{KeyListener, KeyEvent, KeyAdapter}

sealed trait BKeyEvent {
	val ev: KeyEvent
}

case class KeyPressed(ev: KeyEvent) extends BKeyEvent
case class KeyReleased(ev: KeyEvent) extends BKeyEvent
case class KeyTyped(ev: KeyEvent) extends BKeyEvent

trait KeyListenerExtension {
	val self: Component
	
	def onKeyPressed(func: KeyEvent => Unit): Unit =
		self.addKeyListener(new KeyAdapter(){
			override def keyPressed(ev: KeyEvent): Unit = func(ev)
		})
		
	def onKeyReleased(func: KeyEvent => Unit): Unit =
		self.addKeyListener(new KeyAdapter(){
			override def keyPressed(ev: KeyEvent): Unit = func(ev)
		})
		
	def onKeyTyped(func: KeyEvent => Unit): Unit =
		self.addKeyListener(new KeyAdapter(){
			override def keyPressed(ev: KeyEvent): Unit = func(ev)
		})
	
	def addBKeyListener(part: PartialFunction[BKeyEvent, Unit]): Unit = {
		val func = part.lift
		self.addKeyListener(new KeyListener(){
			def keyPressed(ev: KeyEvent): Unit = func(KeyPressed(ev))
			def keyReleased(ev: KeyEvent): Unit = func(KeyReleased(ev))
			def keyTyped(ev: KeyEvent): Unit = func(KeyTyped(ev))
		})
	}
}