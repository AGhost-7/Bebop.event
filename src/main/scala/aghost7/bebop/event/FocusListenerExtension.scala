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
	
	def onFocusGained(func: FocusEvent => Unit) : FocusListener = {
		val l = new FocusAdapter(){
			override def focusGained(ev: FocusEvent) = func(ev)
		}
		self.addFocusListener(l)
		l
	}
		
	
	def onFocusLost(func: FocusEvent => Unit) : FocusListener = {
		val l = new FocusAdapter(){
			override def focusLost(ev: FocusEvent) = func(ev)
		}
		self.addFocusListener(l)
		l
	}
		
	
	def addBFocusListener(part: PartialFunction[BFocusEvent, Unit]): FocusListener = {
		val func = part.lift
		val l = new FocusListener(){
			def focusGained(ev: FocusEvent): Unit = 
				func(FocusGained(ev))
			def focusLost(ev: FocusEvent): Unit = 
				func(FocusLost(ev))
		}
		self.addFocusListener(l)
		l
	}
		
}