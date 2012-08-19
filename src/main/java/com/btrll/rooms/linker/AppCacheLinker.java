package com.btrll.rooms.linker;

import com.btrll.core.linker.SimpleAppCacheLinker;
import com.google.gwt.core.ext.linker.Shardable;

/**
 * A custom linker that generates an app cache manifest with the files generated
 * by the GWT compiler and the static files used by this application.
 * <p>
 * Before using this approach with production code be sure that you understand
 * the limitations of {@link SimpleAppCacheLinker}, namely that it sends all
 * permutations to the client.
 * 
 * @see SimpleAppCacheLinker
 */
@Shardable
public class AppCacheLinker extends SimpleAppCacheLinker {
	@Override
	protected String[] otherCachedFiles() {
		return new String[] { "/index.html", "/images/spinner.png",
				"/favicon.ico", "/config.json", "/d3.v2.min.js",
				"/brightrollSF.svg" };
	}
}
