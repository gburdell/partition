
package partition.pgparse.generated ;


import java.util.List;
import java.util.LinkedList;

import apfe.runtime.*;
import partition.pgparse.*;




public  class Edge extends Acceptor {

    public Edge() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new Name(),
new COLON(),
new Integer(),
new Repetition(new Sequence(new COMMA(),
new Integer()), Repetition.ERepeat.eZeroOrMore),
new Semi()) ;
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
    public Edge create() {
        return new Edge();
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


