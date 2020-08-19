package org.kiwix.kiwixmobile.core.page.bookmark

import org.kiwix.kiwixmobile.core.R
import org.kiwix.kiwixmobile.core.di.components.CoreComponent
import org.kiwix.kiwixmobile.core.extensions.ActivityExtensions.viewModel
import org.kiwix.kiwixmobile.core.page.PageActivity
import org.kiwix.kiwixmobile.core.page.adapter.PageAdapter
import org.kiwix.kiwixmobile.core.page.adapter.PageDelegate.PageItemDelegate
import org.kiwix.kiwixmobile.core.page.bookmark.viewmodel.BookmarkViewModel

class BookmarksActivity : PageActivity() {
  override val pageViewModel by lazy { viewModel<BookmarkViewModel>(viewModelFactory) }

  override val pageAdapter: PageAdapter by lazy {
    PageAdapter(PageItemDelegate(this))
  }

  override val title: String by lazy { getString(R.string.bookmarks) }
  override val noItemsString: String by lazy { getString(R.string.no_bookmarks) }
  override val switchString: String by lazy { getString(R.string.bookmarks_from_current_book) }
  override val switchIsChecked: Boolean by lazy { sharedPreferenceUtil.showBookmarksAllBooks }
  override fun injection(coreComponent: CoreComponent) {
    activityComponent.inject(this)
  }

  override val searchQueryHint: String by lazy { getString(R.string.search_bookmarks) }
}
