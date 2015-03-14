package aghost7.bebop.event

import java.awt.event.{ActionListener, ActionEvent}

trait ActionListenerExtension {
		val self: { def addActionListener(act: ActionListener): Unit }
		
		def onActionPerformed(func: ActionEvent => Unit): Unit = 
			self.addActionListener(new ActionListener(){
				def actionPerformed(ev: ActionEvent): Unit = func(ev)
			})
			
		def addBActionListener(callback: ActionEvent => Unit) : Unit = 
			self.addActionListener(new ActionListener(){
				def actionPerformed(ev: ActionEvent){
					callback(ev)
				}
			})
	}