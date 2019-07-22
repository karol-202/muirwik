package com.ccfraser.muirwik.components

import react.RBuilder
import react.RComponent
import react.RState
import styled.StyledHandler
import styled.StyledProps

@JsModule("@material-ui/core/SvgIcon")
private external val svgIconModule: dynamic

@Suppress("UnsafeCastFromDynamic")
private val svgIconComponent: RComponent<SvgIconProps, RState> = svgIconModule.default

enum class SvgIconColor
{
	inherit, primary, secondary, action, error, disabled
}

interface SvgIconProps : StyledProps
{
	var color: SvgIconColor?
	var component: String?
	var fontSize: MIconFontSize?
	var htmlColor: String?
	var shapeRendering: String?
	var titleAccess: String?
	var viewBox: String?
}

fun RBuilder.mSvgIcon(color: SvgIconColor? = null,
                      component: String? = null,
                      fontSize: MIconFontSize? = null,
                      htmlColor: String? = null,
                      shapeRendering: String? = null,
                      titleAccess: String? = null,
                      viewBox: String? = null,
                      className: String? = null,
                      handler: StyledHandler<SvgIconProps>? = null) = createStyled(svgIconComponent) {
	color?.let { attrs.color = it }
	component?.let { attrs.component = it }
	fontSize?.let { attrs.fontSize = it }
	htmlColor?.let { attrs.htmlColor = it }
	shapeRendering?.let { attrs.shapeRendering = it }
	titleAccess?.let { attrs.titleAccess = it }
	viewBox?.let { attrs.viewBox = it }
	setStyledPropsAndRunHandler(className, handler)
}
