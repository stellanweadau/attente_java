package io.github.oliviercailloux.y2018.apartments.xmcda;

import java.util.Set;

import org.decisiondeck.jmcda.exc.InvalidInputException;
import org.decisiondeck.jmcda.xws.IXWS;
import org.decisiondeck.jmcda.xws.XWSInput;
import org.xmcda.Alternatives;

public class XMCDAHandeler implements IXWS {
	
	@XWSInput
	Set<Alternatives> alternatives;
	
	@Override
	public void execute() throws InvalidInputException {
		return;
	}

}
