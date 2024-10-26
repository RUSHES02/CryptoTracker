package com.example.cryptotracker.crypto.presentation.coin_details

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptotracker.crypto.domain.CoinPrice
import com.example.cryptotracker.ui.theme.CryptoTrackerTheme
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun LineChart(
	dataPoints: List<DataPoint>,
	style: ChartStyle,
	visibleDataPointsIndices: IntRange,
	unit: String,
	modifier: Modifier = Modifier,
	selectedDataPoint: DataPoint? = null,
	onSelectedDataPoint: (DataPoint) -> Unit = {},
	onXLabelWidthChange: (Float) -> Unit = {},
	showHelperLines: Boolean = true
) {
	
	val textStyle = LocalTextStyle.current.copy(
		fontSize = style.labelFontSize,
	)
//	Log.d("LineChart", "dataPoints: $dataPoints")
	
	val visibleDataPoints = remember(dataPoints, visibleDataPointsIndices) {
		dataPoints.slice(visibleDataPointsIndices)
	}
	
	val maxYValue = remember(visibleDataPoints) {
		visibleDataPoints.maxOfOrNull { it.y } ?: 0f
	}
	
	val minYValue = remember(visibleDataPoints) {
		visibleDataPoints.minOfOrNull { it.y } ?: 0f
	}
	
	var measurer = rememberTextMeasurer()
	
	var xLabelWidth by remember {
		mutableFloatStateOf(0f)
	}
	LaunchedEffect(key1 = xLabelWidth) {
		onXLabelWidthChange(xLabelWidth)
	}
	
	val selectedDataPointIndex = remember(selectedDataPoint) {
		dataPoints.indexOf(selectedDataPoint)
	}
	
	var drawPoints by remember {
		mutableStateOf(listOf<DataPoint>())
	}
	
	var isShowingDataPoint by remember {
		mutableStateOf(selectedDataPoint != null)
	}
	
	Canvas(
		modifier = modifier
			.fillMaxSize()
			.pointerInput(drawPoints, xLabelWidth) {
				detectHorizontalDragGestures { change, _ ->
					val newSelectedDataPointIndex = getSelectedDataPoint(
						touchOffsetX = change.position.x,
						triggerWidth = xLabelWidth,
						drawPoints = drawPoints
					)
					isShowingDataPoint =
						(newSelectedDataPointIndex + visibleDataPointsIndices.first) in
							visibleDataPointsIndices
					
					if (isShowingDataPoint) {
						onSelectedDataPoint(dataPoints[newSelectedDataPointIndex])
					}
				}
			}
	) {
		val minLabelSpacingYPx = style.minYLabelSpacing.toPx()
		val verticalPaddingPx = style.verticalPadding.toPx()
		val horizontalPaddingPx = style.horizontalPadding.toPx()
		val xAxisLabelSpacingPx = style.xAxisLabelSpacing.toPx()
		
		val xLabelTextLayoutResults = visibleDataPoints.map {
			measurer.measure(
				text = it.xLabel,
				style = textStyle.copy(textAlign = TextAlign.Center)
			)
		}
		val maxXLabelWidth = xLabelTextLayoutResults.maxOfOrNull { it.size.width } ?: 0
		val maxXLabelHeight = xLabelTextLayoutResults.maxOfOrNull { it.size.height } ?: 0
		val maxXLabelLineCount = xLabelTextLayoutResults.maxOfOrNull { it.lineCount } ?: 0
		val xLabelLineHeight = if (maxXLabelLineCount > 0) {
			maxXLabelHeight / maxXLabelLineCount
		}else 0
		
		val viewPortHeightPx = size.height -
			(xLabelLineHeight + 2 * verticalPaddingPx
				+ maxXLabelHeight + xAxisLabelSpacingPx)
		
		val labelViewPortHeightPx = viewPortHeightPx + xLabelLineHeight
		val labelCountExcludingLast = (labelViewPortHeightPx / (xLabelLineHeight + minLabelSpacingYPx)).toInt()
		
		val valueIncrement = (maxYValue - minYValue) / labelCountExcludingLast
		
		val yLabels = (0..labelCountExcludingLast).map {
			ValueLabel(
				value = maxYValue - (valueIncrement * it),
				unit = unit,
			)
		}
		
		val yLabelTextLayoutResults = yLabels.map {
			measurer.measure(
				text = it.format(),
				style = textStyle
			)
		}
		
		//Y label calculations
		val heightRequiredForLabels = xLabelLineHeight * (labelCountExcludingLast + 1)
		val remainingHeightForLabels = labelViewPortHeightPx - heightRequiredForLabels
		val spaceBetweenLabels =  remainingHeightForLabels / labelCountExcludingLast
		val maxYLabelWidth = yLabelTextLayoutResults.maxOfOrNull { it.size.width } ?: 0
		val viewPortTopY = verticalPaddingPx + xLabelLineHeight + 10f
		val viewPortRightX = size.width
		val viewPortBottomY = viewPortTopY + viewPortHeightPx
		val viewPortLeftX = 2f * horizontalPaddingPx + maxYLabelWidth
		
//		val viewPort = Rect(
//			left = viewPortLeftX,
//			top = viewPortTopY,
//			right = viewPortRightX,
//			bottom = viewPortBottomY
//		)
		
//		drawRect(
//			color = Color.Green.copy(alpha = 0.3f),
//			topLeft = viewPort.topLeft,
//			size = viewPort.size
//		)
		
		xLabelWidth = maxXLabelWidth + xAxisLabelSpacingPx
		xLabelTextLayoutResults.forEachIndexed { index, result ->
			val x = viewPortLeftX + xAxisLabelSpacingPx / 2f + xLabelWidth * index
			drawText(
				textLayoutResult = result,
				topLeft = Offset(
					x = x,
					y = viewPortBottomY + xAxisLabelSpacingPx,
				),
				color = if (index == selectedDataPointIndex){
					style.selectedColor
				}else{
					style.unselectedColor
				}
			)
			
			if(showHelperLines) {
				drawLine(
					color = if (index == selectedDataPointIndex) {
						style.selectedColor
					} else {
						style.unselectedColor
					},
					start = Offset(
						x = x + result.size.height / 2f,
						y = viewPortBottomY
					),
					end = Offset(
						x = x + result.size.height / 2f,
						y = viewPortTopY
					),
					strokeWidth = if (selectedDataPointIndex == index){
						style.axisLineThickness * 1.8f
					}else{
						style.axisLineThickness
					}
				)
			}
			
			if (selectedDataPointIndex == index){
				val valueLabel = ValueLabel(
					value = visibleDataPoints[index].y,
					unit = unit
				)
				val valueResults = measurer.measure(
					text = valueLabel.format(),
					style = textStyle.copy(color = style.selectedColor),
					maxLines = 1
				)
				val textPositionX = if(selectedDataPointIndex == visibleDataPointsIndices.last){
					x - valueResults.size.width
				}else{
					x -  valueResults.size.width / 2f
				} + result.size.width / 2f
				val isTextInVisibleRange = (size.width - textPositionX).roundToInt() in 0..size.width.roundToInt()
				if(isTextInVisibleRange){
					drawText(
						textLayoutResult = valueResults,
						topLeft = Offset(
							x = textPositionX,
							y = viewPortTopY - valueResults.size.height - 10f
						)
					)
				}
			}
		}
		
		yLabelTextLayoutResults.forEachIndexed { index, result ->
			val x = horizontalPaddingPx + maxYLabelWidth - result.size.width
			val y = viewPortTopY + (xLabelLineHeight + spaceBetweenLabels) * index - xLabelLineHeight / 2f
			drawText(
				textLayoutResult = result,
				topLeft = Offset(
					x = x,
					y = y
				),
				color = if (index == selectedDataPointIndex){
					style.selectedColor
				}else{
					style.unselectedColor
				}
			)
			
			if(showHelperLines){
				drawLine(
					color = style.unselectedColor,
					start = Offset(
						x = viewPortLeftX,
						y = y + result.size.height.toFloat() / 2f
					),
					end = Offset(
						x = viewPortRightX,
						y = y + result.size.height.toFloat() / 2f
					),
					strokeWidth = style.axisLineThickness
				)
			}
		}
		
		drawPoints = visibleDataPointsIndices.map {
			Log.d("LineChart", it.toString())
			val x = viewPortLeftX + (it - visibleDataPointsIndices.first) * xLabelWidth + xLabelWidth / 2f
			val ratio = (dataPoints[it].y - minYValue) / (maxYValue - minYValue)
			val y = viewPortBottomY - (ratio * viewPortHeightPx)
			DataPoint(
				x = x,
				y = y,
				xLabel = dataPoints[it].xLabel
			)
		}
		Log.d("LineChart", "drawPoints: $drawPoints")
		val conPoints1 = mutableListOf<DataPoint>()
		val conPoints2 = mutableListOf<DataPoint>()
		for (i in 1 until drawPoints.size ){
			val p0 = drawPoints[i - 1]
			val p1 = drawPoints[i]
			
			val x = (p1.x + p0.x) / 2f
			val y0 = p0.y
			val y1 = p1.y
			
			conPoints1.add(DataPoint(x, y0, ""))
			conPoints2.add(DataPoint(x, y1, ""))
		}
		
		
		val linePath = Path().apply {
			if (drawPoints.isNotEmpty()){
				moveTo(drawPoints.first().x, drawPoints.first().y)
				
				for (i in 1 until drawPoints.size){
					cubicTo(
						x1 = conPoints1[i - 1].x,
						y1 = conPoints1[i - 1].y,
						x2 = conPoints2[i -1].x,
						y2 = conPoints2[i - 1].y,
						x3 = drawPoints[i].x,
						y3 = drawPoints[i].y
					)
				}
			}
		}
		drawPath(
			path = linePath,
			color = style.chartLineColor,
			style = Stroke(
				width = 5f,
				cap = StrokeCap.Round
			)
		)
		
		drawPoints.forEachIndexed {index, point ->
			if (isShowingDataPoint){
				val circleOffset = Offset(
					x = point.x,
					y = point.y
				)
				drawCircle(
					radius = 10f,
					color = style.selectedColor,
					center = circleOffset
				)
				
				if (selectedDataPointIndex == index){
					drawCircle(
						radius = 15f,
						color = Color.White,
						center = circleOffset
					)
					
					drawCircle(
						radius = 15f,
						color = style.selectedColor,
						center = circleOffset,
						style = Stroke(
							width = 3f
						)
					)
				}
			}
		}
	}
}

private fun getSelectedDataPoint(
	touchOffsetX: Float,
	triggerWidth: Float,
	drawPoints: List<DataPoint>
): Int {
	val triggerRangeLeft = touchOffsetX - triggerWidth / 2f
	val triggerRangeRight = touchOffsetX + triggerWidth / 2f
	
	return drawPoints.indexOfFirst {
		it.x in triggerRangeLeft..triggerRangeRight
	}
}

@Preview(widthDp = 1000)
@Composable
private fun LineChartPreview() {
	CryptoTrackerTheme {
		val coinHistory = remember {
			(1..20).map{
				CoinPrice(
					priceUsd = Random.nextFloat() * 1000.0,
					dateTime = ZonedDateTime.now().plusHours(it.toLong())
				)
			}
		}
		
		val style = ChartStyle(
			chartLineColor = Color.Black,
			unselectedColor = Color(0xff7c7c7c),
			selectedColor = Color.Black,
			helperLineThickness = 5f,
			axisLineThickness = 1f,
			labelFontSize = 14.sp,
			minYLabelSpacing = 25.dp,
			verticalPadding = 8.dp,
			horizontalPadding = 8.dp,
			xAxisLabelSpacing = 8.dp
		)
		
		val dataPoints = remember {
			coinHistory.map {
				DataPoint(
					x = it.dateTime.hour.toFloat(),
					y = it.priceUsd.toFloat(),
					xLabel = DateTimeFormatter
						.ofPattern("ha\nM/d")
						.format(it.dateTime),
				)
			}
		}
		
		LineChart(
			dataPoints = dataPoints,
			style = style,
			visibleDataPointsIndices = 0..19,
			unit = "$",
			modifier = Modifier
				.size(700.dp, 300.dp)
				.background(Color.White),
			selectedDataPoint = dataPoints[1],
			
		)
	}
}