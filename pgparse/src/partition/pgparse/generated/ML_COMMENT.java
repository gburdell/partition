
package partition.pgparse.generated ;


import java.util.List;
import java.util.LinkedList;

import apfe.runtime.*;
import partition.pgparse.*;




public  class ML_COMMENT extends Acceptor {

    public ML_COMMENT() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new CharSeq("/*"),
new Repetition(new Sequence(new NotPredicate(new CharSeq("*/")),
new CharClass(ICharClass.IS_ANY)), Repetition.ERepeat.eZeroOrMore),
new CharSeq("*/")) ;
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
    public ML_COMMENT create() {
        return new ML_COMMENT();
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


