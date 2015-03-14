package aghost7.bebop.event

import java.awt.Component
import java.awt.event.{FocusEvent, FocusListener, FocusAdapter}

sealed trait BFocusEvent {
	val ev: FocusEvent
}

case class FocusGained(ev: FocusEvent) extends BFocusEvent
case class FocusLost(ev: FocusEvent) extends BFocusEvent

trait FocusListenerExtension {
	val self: Component
	
	def onFocusGained(func: FocusEvent => Unit) : Unit =
		self.addFocusListener(new FocusAdapter(){
			override def focusGained(ev: FocusEvent) = func(ev)
		})
	
	def onFocusLost(func: FocusEvent => Unit) : Unit =
		self.addFocusListener(new FocusAdapter(){
			override def focusLost(ev: FocusEvent) = func(ev)
		})
	
	def addBFocusListener(part: PartialFunction[BFocusEvent, Unit]): Unit = {
		val func = part.lift
		self.addFocusListener(new FocusListener(){
			def focusGained(ev: FocusEvent): Unit = 
				func(FocusGained(ev))
			def focusLost(ev: FocusEvent): Unit = 
				func(FocusLost(ev))
		})
	}
		
}