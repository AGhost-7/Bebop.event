package aghost7.bebop.event

import javax.swing._
import java.awt._
import java.awt.event._

package object implicits {
	
	implicit class buttonExtensions(val self: Button) 
		extends ActionListenerExtension
	
	implicit class menuItemExtensions(val self: MenuItem)
		extends ActionListenerExtension
	
	implicit class checkboxMenuItemExtensions(val self: CheckboxMenuItem)
		extends ItemListenerExtension
		
	implicit class windowExtensions(val self: Window)
		extends WindowListenerExtension
		with WindowFocusListenerExtension
		with WindowStateListenerExtension
	
	implicit class componentExtensions(val self: Component)
		extends ComponentListenerExtension
		with FocusListenerExtension
		with KeyListenerExtension
		with MouseListenerExtension
		with MouseMotionListenerExtension
		with MouseWheelListenerExtension

	// swing specific
	implicit class abstractButtonExtensions(val self: AbstractButton)
		extends ActionListenerExtension
	
	implicit class jMenuItemExtensions(val self: JMenuItem)
		extends ChangeListenerExtension
		
		
}