/* AWTEvent.java -- the root event in AWT
   Copyright (C) 1999, 2000, 2002 Free Software Foundation

This file is part of GNU Classpath.

GNU Classpath is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.

GNU Classpath is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with GNU Classpath; see the file COPYING.  If not, write to the
Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
02111-1307 USA.

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent
modules, and to copy and distribute the resulting executable under
terms of your choice, provided that you also meet, for each linked
independent module, the terms and conditions of the license of that
module.  An independent module is a module which is not derived from
or based on this library.  If you modify this library, you may extend
this exception to your version of the library, but you are not
obligated to do so.  If you do not wish to do so, delete this
exception statement from your version. */


package java.awt;

import java.util.EventObject;

/**
 * AWTEvent is the root event class for all AWT events in the JDK 1.1 event 
 * model. It supersedes the Event class from JDK 1.0. Subclasses outside of
 * the java.awt package should have IDs greater than RESERVED_ID_MAX.
 *
 * <p>Event masks defined here are used by components in
 * <code>enableEvents</code> to select event types not selected by registered
 * listeners. Event masks are appropriately set when registering on
 * components.
 *
 * @author Warren Levy  <warrenl@cygnus.com>
 * @author Aaron M. Renn <arenn@urbanophile.com>
 * @since 1.1
 * @status updated to 1.4
 */
public abstract class AWTEvent extends EventObject
{
  /**
   * Compatible with JDK 1.1+.
   */
  private static final long serialVersionUID = -1825314779160409405L;

  /**
   * The ID of the event.
   *
   * @see #getID()
   * @see #AWTEvent(Object, int)
   * @serial the identifier number of this event
   */
  protected int id;

  /**
   * Indicates if the event has been consumed. False mean it is passed to
   * the peer, true means it has already been processed. Semantic events
   * generated by low-level events always have the value true.
   *
   * @see #consume()
   * @see #isConsumed()
   * @serial whether the event has been consumed
   */
  protected boolean consumed;

  /**
   * Who knows? It's in the serial version.
   *
   * @serial No idea what this is for.
   */
  byte[] bdata;

  /** Mask for selecting component events. */
  public static final long COMPONENT_EVENT_MASK = 0x00001;

  /** Mask for selecting container events. */
  public static final long CONTAINER_EVENT_MASK = 0x00002;

  /** Mask for selecting component focus events. */
  public static final long FOCUS_EVENT_MASK = 0x00004;

  /** Mask for selecting keyboard events. */
  public static final long KEY_EVENT_MASK = 0x00008;

  /** Mask for mouse button events. */
  public static final long MOUSE_EVENT_MASK = 0x00010;

  /** Mask for mouse motion events. */
  public static final long MOUSE_MOTION_EVENT_MASK = 0x00020;

  /** Mask for window events. */
  public static final long WINDOW_EVENT_MASK = 0x00040;

  /** Mask for action events. */
  public static final long ACTION_EVENT_MASK = 0x00080;

  /** Mask for adjustment events. */
  public static final long ADJUSTMENT_EVENT_MASK = 0x00100;

  /** Mask for item events. */
  public static final long ITEM_EVENT_MASK = 0x00200;

  /** Mask for text events. */
  public static final long TEXT_EVENT_MASK = 0x00400;

  /**
   * Mask for input method events.
   * @since 1.3
   */
  public static final long INPUT_METHOD_EVENT_MASK = 0x00800;

  /**
   * Mask if input methods are enabled. Package visible only.
   */
  static final long INPUT_ENABLED_EVENT_MASK = 0x01000;

  /**
   * Mask for paint events.
   * @since 1.3
   */
  public static final long PAINT_EVENT_MASK = 0x02000;

  /**
   * Mask for invocation events.
   * @since 1.3
   */
  public static final long INVOCATION_EVENT_MASK = 0x04000;

  /**
   * Mask for hierarchy events.
   * @since 1.3
   */
  public static final long HIERARCHY_EVENT_MASK = 0x08000;

  /**
   * Mask for hierarchy bounds events.
   * @since 1.3
   */
  public static final long HIERARCHY_BOUNDS_EVENT_MASK = 0x10000;

  /**
   * Mask for mouse wheel events.
   * @since 1.4
   */
  public static final long MOUSE_WHEEL_EVENT_MASK = 0x20000;

  /**
   * Mask for window state events.
   * @since 1.4
   */
  public static final long WINDOW_STATE_EVENT_MASK = 0x40000;

  /**
   * Mask for window focus events.
   * @since 1.4
   */
  public static final long WINDOW_FOCUS_EVENT_MASK = 0x80000;

  /**
  * This is the highest number for event ids that are reserved for use by
  * the AWT system itself. Subclasses outside of java.awt should use higher
  * ids.
  */
  public static final int RESERVED_ID_MAX = 1999;


  /**
   * Initializes a new AWTEvent from the old Java 1.0 event object.
   *
   * @param event the old-style event
   * @throws NullPointerException if event is null
   */
  public AWTEvent(Event event)
  {
    this(event.target, event.id);
    consumed = event.consumed;
  }

  /**
   * Create an event on the specified source object and id.
   *
   * @param source the object that caused the event
   * @param id the event id
   * @throws IllegalArgumentException if source is null
   */
  public AWTEvent(Object source, int id)
  {
    super(source);
    this.id = id;
  }

  /**
   * Retarget the event, such as converting a heavyweight component to a
   * lightweight child of the original. This is not for general use, but
   * is for event targeting systems like KeyboardFocusManager.
   *
   * @param source the new source
   */
  public void setSource(Object source)
  {
    this.source = source;
  }

  /**
   * Returns the event type id.
   *
   * @return the id number of this event
   */
  public int getID()
  {
    return id;
  }

  /**
   * Returns a string representation of this event. This is in the format
   * <code>getClass().getName() + '[' + paramString() + "] on "
   * + source</code>.
   *
   * @return a string representation of this event
   */
  public String toString()
  {
    return getClass().getName() + "[" + paramString() + "] on " + source;
  }

  /**
   * Returns a string representation of the state of this event. It may be
   * empty, but must not be null; it is implementation defined.
   *
   * @return a string representation of this event
   */
  public String paramString()
  {
    return "";
  }

  /**
   * Consumes this event so that it will not be processed in the default
   * manner.
   */
  protected void consume()
  {
    consumed = true;
  }

  /**
   * Tests whether not not this event has been consumed. A consumed event
   * is not processed in the default manner.
   *
   * @return true if this event has been consumed
   */
  protected boolean isConsumed()
  {
    return consumed;
  }
} // class AWTEvent
