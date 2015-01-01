
package partition.pgparse.generated ;


import java.util.List;
import java.util.LinkedList;

import apfe.runtime.*;
import partition.pgparse.*;




public  class Grammar extends Acceptor {

    public Grammar() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new Spacing(),
new Repetition(new NameMap(), Repetition.ERepeat.eOptional),
new Repetition(new CellEntry(), Repetition.ERepeat.eZeroOrMore),
new Repetition(new Edges(), Repetition.ERepeat.eOptional),
new EOF()) ;
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
    public Grammar create() {
        return new Grammar();
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

