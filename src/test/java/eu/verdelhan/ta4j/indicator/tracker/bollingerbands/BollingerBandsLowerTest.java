package eu.verdelhan.ta4j.indicator.tracker.bollingerbands;

import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicator.helper.StandardDeviation;
import eu.verdelhan.ta4j.indicator.simple.ClosePrice;
import eu.verdelhan.ta4j.indicator.tracker.SMA;
import eu.verdelhan.ta4j.mocks.MockTimeSeries;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class BollingerBandsLowerTest {

	private TimeSeries data;

	private int timeFrame;

	private ClosePrice closePrice;

	private SMA sma;

	@Before
	public void setUp() throws Exception {
		data = new MockTimeSeries(new double[] { 1, 2, 3, 4, 3, 4, 5, 4, 3, 3, 4, 3, 2 });
		timeFrame = 3;
		closePrice = new ClosePrice(data);
		sma = new SMA(closePrice, timeFrame);
	}

	@Test
	public void testBollingerBandsLowerUsingSMAAndStandardDeviation() throws Exception {

		BollingerBandsMiddle bbmSMA = new BollingerBandsMiddle(sma);
		StandardDeviation standardDeviation = new StandardDeviation(closePrice, timeFrame);
		BollingerBandsLower bblSMA = new BollingerBandsLower(bbmSMA, standardDeviation);

		assertThat((double) bblSMA.getValue(0)).isEqualTo(1d);
		assertThat(bblSMA.getValue(1)).isEqualTo(0.08);
		assertEquals(-0.82, bblSMA.getValue(2), 0.01);
		assertThat(bblSMA.getValue(3)).isEqualTo(0.17);
		assertThat(bblSMA.getValue(4)).isEqualTo(1.70);
		assertThat(bblSMA.getValue(5)).isEqualTo(2.03);
		assertThat(bblSMA.getValue(6)).isEqualTo(1.17);
	}

	@Test
	public void testBollingerBandsLowerShouldWorkJumpingIndexes() {

		BollingerBandsMiddle bbmSMA = new BollingerBandsMiddle(sma);
		StandardDeviation standardDeviation = new StandardDeviation(closePrice, timeFrame);
		BollingerBandsLower bblSMA = new BollingerBandsLower(bbmSMA, standardDeviation);

		assertThat(bblSMA.getValue(6)).isEqualTo(1.17);
		assertThat(bblSMA.getValue(1)).isEqualTo(0.08);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testIndexGreatterThanTheIndicatorLenghtShouldThrowException() {

		BollingerBandsMiddle bbmSMA = new BollingerBandsMiddle(sma);
		StandardDeviation standardDeviation = new StandardDeviation(closePrice, timeFrame);
		BollingerBandsLower bblSMA = new BollingerBandsLower(bbmSMA, standardDeviation);

		bblSMA.getValue(data.getSize());
	}
}