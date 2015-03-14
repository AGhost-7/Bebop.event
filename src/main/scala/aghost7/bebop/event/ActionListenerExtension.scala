package aghost7.bebop.event

import java.awt.event.{ActionListener, ActionEvent}

trait ActionListenerExtension {
		val self: { def addActionListener(act: ActionListener): Unit }
		
		def onActionPerformed(func: ActionEvent => Unit): ActionListener = {
			val l = new ActionListener(){
				def actionPerformed(ev: ActionEvent): Unit = func(ev)
			}
			self.addActionListener(l)
			l
		}
		
		def addBActionListener(func: ActionEvent => Unit) : ActionListener = 
			onActionPerformed(func)
			
}