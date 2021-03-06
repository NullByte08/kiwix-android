/*
 * Kiwix Android
 * Copyright (c) 2020 Kiwix <android.kiwix.org>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.kiwix.kiwixmobile.core.search.viewmodel

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.kiwix.kiwixmobile.core.reader.ZimFileReader
import org.kiwix.kiwixmobile.core.reader.ZimReaderContainer
import org.kiwix.kiwixmobile.core.search.SearchSuggestion
import org.kiwix.kiwixmobile.core.search.adapter.SearchListItem.ZimSearchResultListItem
import org.kiwix.kiwixmobile.core.utils.SharedPreferenceUtil

internal class ZimSearchResultGeneratorTest {

  private val sharedPreferenceUtil: SharedPreferenceUtil = mockk()
  private val zimReaderContainer: ZimReaderContainer = mockk()
  private val zimFileReader: ZimFileReader = mockk()

  private val zimSearchResultGenerator: ZimSearchResultGenerator =
    ZimSearchResultGenerator(zimReaderContainer)

  @BeforeEach
  internal fun setUp() {
    every { zimReaderContainer.copyReader() } returns zimFileReader
  }

  @Test
  internal fun `empty search term returns empty list`() {
    assertThat(zimSearchResultGenerator.generateSearchResults(""))
      .isEqualTo(emptyList<ZimSearchResultListItem>())
  }

  @Test
  internal fun `suggestion results are distinct`() {
    val validTitle = "title"
    val searchTerm = " "
    val item = mockk<SearchSuggestion>()
    every { zimFileReader.searchSuggestions(" ", 200) } returns true
    every { zimFileReader.getNextSuggestion() } returnsMany listOf(item, item, null)
    every { item.title } returns validTitle
    assertThat(zimSearchResultGenerator.generateSearchResults(searchTerm))
      .isEqualTo(listOf(ZimSearchResultListItem(validTitle)))
    verify {
      zimFileReader.searchSuggestions(searchTerm, 200)
      zimFileReader.dispose()
    }
  }
}
