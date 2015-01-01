
package partition.pgparse.generated ;


import java.util.List;
import java.util.LinkedList;

import apfe.runtime.*;
import partition.pgparse.*;




public  class SEMI extends Acceptor {

    public SEMI() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq(';'),
new Spacing()) ;
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
    public SEMI create() {
        return new SEMI();
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


