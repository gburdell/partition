
package partition.pgparse.generated ;


import java.util.List;
import java.util.LinkedList;

import apfe.runtime.*;
import partition.pgparse.*;




public  class Float extends Acceptor {

    public Float() {
    }

    @Override
    protected boolean accepti() {
		Acceptor matcher = new Sequence(new Repetition(new CharClass(CharClass.matchRange('0','9')), Repetition.ERepeat.eOneOrMore),
new Repetition(new Sequence(new CharSeq('.'),
new Repetition(new CharClass(CharClass.matchRange('0','9')), Repetition.ERepeat.eOneOrMore)), Repetition.ERepeat.eOptional),
new Repetition(new Sequence(new CharClass(CharClass.matchOneOf('e'),
CharClass.matchOneOf('E')),
new Repetition(new CharSeq('-'), Repetition.ERepeat.eOptional),
new Repetition(new CharClass(CharClass.matchRange('0','9')), Repetition.ERepeat.eOneOrMore)), Repetition.ERepeat.eOptional),
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
    public Float create() {
        return new Float();
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


