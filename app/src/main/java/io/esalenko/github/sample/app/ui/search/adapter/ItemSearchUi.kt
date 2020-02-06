package io.esalenko.github.sample.app.ui.search.adapter

import android.text.TextUtils
import android.view.Gravity
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet.MATCH_CONSTRAINT_SPREAD
import androidx.constraintlayout.widget.ConstraintSet.MATCH_CONSTRAINT_WRAP
import androidx.constraintlayout.widget.ConstraintSet.PARENT_ID
import androidx.core.view.setPadding
import io.esalenko.github.sample.app.R
import io.esalenko.github.sample.app.helper.materialCardView
import io.esalenko.github.sample.app.helper.materialTextView
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout


class ItemSearchUi : AnkoComponent<ItemSearch> {

    companion object {
        fun newInstance() = ItemSearchUi()
    }

    override fun createView(ui: AnkoContext<ItemSearch>): View {
        return with(ui) {
            frameLayout {
                lparams(matchParent, wrapContent)
                materialCardView {
                    id = R.id.item_card
                    elevation = 8f
                    radius = 4f
                    useCompatPadding = true

                    constraintLayout {

                        val itemName = materialTextView {
                            id = R.id.item_full_name
                            textAppearance = R.style.TextAppearance_AppCompat_Title
                            maxLines = 1
                            ellipsize = TextUtils.TruncateAt.END
                        }.lparams(0, wrapContent) {
                            margin = dip(4)
                            matchConstraintDefaultWidth = MATCH_CONSTRAINT_SPREAD
                            matchConstraintDefaultHeight = MATCH_CONSTRAINT_WRAP
                            horizontalBias = 0.0f
                        }

                        val itemDescription = materialTextView {
                            id = R.id.item_description
                            textAppearance = R.style.TextAppearance_AppCompat_Body2
                            ellipsize = TextUtils.TruncateAt.END
                            maxLines = 2
                        }.lparams(0, wrapContent) {
                            setMargins(dip(4), dip(12), dip(4), 0)
                            matchConstraintDefaultWidth = MATCH_CONSTRAINT_SPREAD
                            matchConstraintDefaultHeight = MATCH_CONSTRAINT_WRAP
                            horizontalBias = 0.0f
                        }

                        val itemLang = materialTextView {
                            id = R.id.item_language
                            textAppearance = R.style.TextAppearance_AppCompat_Body2
                        }.lparams(wrapContent, wrapContent) {
                            setMargins(0, dip(12), 0, 0)
                            matchConstraintDefaultWidth = MATCH_CONSTRAINT_WRAP
                            matchConstraintDefaultHeight = MATCH_CONSTRAINT_WRAP
                            horizontalBias = 0.0f
                        }

                        val itemStars = materialTextView {
                            id = R.id.item_stars
                            textAppearance = R.style.TextAppearance_AppCompat_Body2
                            gravity = Gravity.CENTER
                            setCompoundDrawablesRelativeWithIntrinsicBounds(
                                R.drawable.ic_star_24px,
                                0,
                                0,
                                0
                            )
                        }.lparams(wrapContent, wrapContent) {
                            horizontalBias = 0.0f
                            setMargins(0, dip(12), 0, 0)
                            matchConstraintDefaultWidth = MATCH_CONSTRAINT_WRAP
                            matchConstraintDefaultHeight = MATCH_CONSTRAINT_WRAP
                        }

                        val itemDelete = imageView {
                            id = R.id.item_delete_icon
                            setImageResource(R.drawable.ic_round_clear_24px)
                            setPadding(12)
                        }.lparams(wrapContent, wrapContent) {
                            horizontalBias = 0.0f
                            matchConstraintDefaultWidth = MATCH_CONSTRAINT_WRAP
                            matchConstraintDefaultHeight = MATCH_CONSTRAINT_WRAP
                        }

                        applyConstraintSet {

                            itemName {
                                connect(
                                    START to START of PARENT_ID,
                                    TOP to TOP of PARENT_ID,
                                    BOTTOM to TOP of R.id.item_description,
                                    END to START of R.id.item_delete_icon
                                )
                            }

                            itemDescription {
                                connect(
                                    START to START of PARENT_ID,
                                    END to END of PARENT_ID,
                                    TOP to BOTTOM of R.id.item_full_name
                                )
                            }

                            itemLang {
                                connect(
                                    START to START of R.id.item_description,
                                    BOTTOM to BOTTOM of PARENT_ID,
                                    TOP to BOTTOM of R.id.item_description
                                )
                            }

                            itemStars {
                                connect(
                                    BOTTOM to BOTTOM of PARENT_ID,
                                    START to END of R.id.item_language,
                                    TOP to BOTTOM of R.id.item_description
                                )
                            }

                            itemDelete {
                                connect(
                                    BOTTOM to TOP of R.id.item_description,
                                    END to END of PARENT_ID,
                                    TOP to TOP of PARENT_ID
                                )
                            }
                        }
                    }.lparams(matchParent, matchParent)
                }
            }
        }
    }
}
