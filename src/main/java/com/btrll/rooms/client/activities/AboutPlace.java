/*
 * Copyright 2010 Daniel Kurka
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.btrll.rooms.client.activities;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * @author Daniel Kurka
 * 
 */
public class AboutPlace extends Place {
	@Prefix(PREFIX)
	public static class Tokenizer implements PlaceTokenizer<AboutPlace> {

		@Override
		public AboutPlace getPlace(String token) {
			return new AboutPlace();
		}

		@Override
		public String getToken(AboutPlace place) {
			return "";
		}

	}

	private static final String PREFIX = "about";

	@Override
	public String toString() {
		return PREFIX + ":";
	}

}
