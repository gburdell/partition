
package partition.pgparse.generated ;


import java.util.List;
import java.util.LinkedList;

import apfe.runtime.*;
import partition.pgparse.*;




public  class NameMap extends Acceptor {

    public NameMap() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new NAMEMAP_K(),
new LCURLY(),
new Repetition(new NameMapEntry(), Repetition.ERepeat.eZeroOrMore),
new RCURLY()) ;
		m_baseAccepted = match(matcher);
		boolean match = (null != m_baseAccepted);

        if (match && (null != getListeners())) {
            for (Listener cb : getListeners()) {
                cb.onAccept(m_baseAccepted);
            }
        }

        return match;
    }

    @Override
    public NameMap create() {
        return new NameMap();
    }

 


	//Begin Listener
	static //@Override
	public void addListener(final Listener listener) {
		if (null == stListeners) {
			stListeners = new LinkedList<>();
		}
		stListeners.add(listener);
	}

	@Override
    protected Iterable<Listener> getListeners() {
		return stListeners;
	}

	private static List<Listener> stListeners;
	//End Listener

}


