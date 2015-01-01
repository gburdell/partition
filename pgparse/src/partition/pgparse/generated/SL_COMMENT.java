
package partition.pgparse.generated ;


import java.util.List;
import java.util.LinkedList;

import apfe.runtime.*;
import partition.pgparse.*;




public  class SL_COMMENT extends Acceptor {

    public SL_COMMENT() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("//"),
new Repetition(new Sequence(new NotPredicate(new EOL()),
new CharClass(ICharClass.IS_ANY)), Repetition.ERepeat.eZeroOrMore),
new EOL()) ;
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
    public SL_COMMENT create() {
        return new SL_COMMENT();
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

