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
package com.btrll.rooms.client;

import com.btrll.rooms.client.activities.AboutPlace.AboutPlaceTokenizer;
import com.btrll.rooms.client.activities.animation.AnimationPlace.AnimationPlaceTokenizer;
import com.btrll.rooms.client.activities.pulltorefresh.PullToRefreshPlace;
import com.btrll.rooms.client.places.HomePlace.HomePlaceTokenizer;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

/**
 * @author Daniel Kurka
 * 
 */
@WithTokenizers({ HomePlaceTokenizer.class, AboutPlaceTokenizer.class,
		AnimationPlaceTokenizer.class, PullToRefreshPlace.Tokenizer.class, })
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}
