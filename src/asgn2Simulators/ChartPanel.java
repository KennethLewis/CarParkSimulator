package asgn2Simulators;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;


/***
 * Class for chart generation
 * @author Thomas McCarthy
 * @author Ken Lewis
 *
 */
public class ChartPanel extends GUISimulator {
	private static final long serialVersionUID=100L;
	
	private String chartTitle;
	private JDialog chartDialog;
	private ArrayList<String> dataList;
	private ArrayList<Integer> dataProcessList = new ArrayList<Integer>();
	
	/***
	 * Object used for created various graphs.
	 * 
	 * @param title
	 *            The title of the graph
	 * @param statusList
	 *            List of carpark statuses to eventually pull data from
	 * @param isSummary
	 *            If true, then it is a summary bar graph
	 * @Author Thomas McCarthy
	 */
    public ChartPanel(String title, ArrayList<String> statusList, boolean isSummary) {
    	super(title);
    	
    	this.dataList = statusList;
    	
    	chartDialog = new JDialog(this, title, true);
        chartTitle = title;
        JFreeChart chart;
        
        if (!isSummary) {
        final TimeSeriesCollection dataset = createTimeSeriesData(); 
        	chart = createTimeChart(dataset);
        } else {
        	final CategoryDataset dataset = createBarChartData();
        	chart = createBarChart(dataset);
        }
        chartDialog.add(new org.jfree.chart.ChartPanel(chart), BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout());
        chartDialog.add(btnPanel, BorderLayout.SOUTH);
        
		chartDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    public JDialog getChartDialog() {
    	return chartDialog;
    }
    
    /***
     * Helper method that creates the bar chart data from a list of statuses
     * @return the processed data, ready for bar chart implementation
     * @Author Thomas McCarthy
     * @Author Ken Lewis
     */
    private CategoryDataset createBarChartData() {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (int i=0; i<=Constants.CLOSING_TIME; i++) {
        	 processData(dataList.get(i));
 	        
            dataset.addValue(dataProcessList.get(1), "Total Vehicles", "");
            dataset.addValue(dataProcessList.get(6), "Dissastisfied Vehicles", "");
            dataProcessList.clear();
        }


        return dataset;
        
    }
    
    /**
     * Private method creates the dataset. Lots of hack code in the 
     * middle, but you should use the labelled code below  
	 * @return collection of time series for the plot 
	 */
	private TimeSeriesCollection createTimeSeriesData() {
		TimeSeriesCollection tsc = new TimeSeriesCollection(); 
		TimeSeries vehiclesToDate = new TimeSeries("Vehicles To Date");
		TimeSeries currentlyParked = new TimeSeries("Currently Parked");
		TimeSeries totalCars = new TimeSeries("Total Cars");
		TimeSeries smallCarTotal = new TimeSeries("Total Small Cars");
		TimeSeries motorCycleTotal = new TimeSeries("Total MotorCycles");
		TimeSeries vehiclesInQueue = new TimeSeries("Vehicles in Queue"); 
		TimeSeries archivedVehicles = new TimeSeries("Archived Vehicles");
		TimeSeries dissatisfiedVehicles = new TimeSeries("Dissatisfied Vehicles");


		//Base time, data set up - the calendar is needed for the time points
		Calendar cal = GregorianCalendar.getInstance();


		for (int i=0; i<=Constants.CLOSING_TIME; i++) {
			//These lines are important 
			cal.set(2014,0,1,6,i);
	        Date timePoint = cal.getTime();
	        
	       
	        processData(dataList.get(i));
	        
	     
	        
	        //This is important - steal it shamelessly 
			vehiclesToDate.add(new Minute(timePoint),dataProcessList.get(1));
			currentlyParked.add(new Minute(timePoint),dataProcessList.get(2));
			totalCars.add(new Minute(timePoint),dataProcessList.get(3));
			smallCarTotal.add(new Minute(timePoint),dataProcessList.get(4));
			motorCycleTotal.add(new Minute(timePoint),dataProcessList.get(5));
			vehiclesInQueue.add(new Minute(timePoint),dataProcessList.get(8));
			archivedVehicles.add(new Minute(timePoint),dataProcessList.get(7));
			dissatisfiedVehicles.add(new Minute(timePoint),dataProcessList.get(6));
			dataProcessList.clear();
		}
		
		//Collection
		tsc.addSeries(vehiclesToDate);
		tsc.addSeries(currentlyParked);
		tsc.addSeries(totalCars);
		tsc.addSeries(smallCarTotal);
		tsc.addSeries(motorCycleTotal);
		tsc.addSeries(vehiclesInQueue);
		
		tsc.addSeries(archivedVehicles);
		tsc.addSeries(dissatisfiedVehicles);
		
		return tsc; 
	}
	

	/***
	 * Helper method that processed carpark statuses and rips out
	 * numbers from the string
	 * @param statusString the status string to pull from
	 */
	private void processData(String statusString) {
		
		Pattern pattern = Pattern.compile("[0-9]+"); 
		Matcher matcher = pattern.matcher(statusString);

		
		// Find all matches
		while (matcher.find()) { 
		  // Get the matching string  
			dataProcessList.add(Integer.parseInt(matcher.group()));
		}
		

	}
    /**
     * Helper method to deliver the Chart - currently uses default colours and auto range 
     * @param dataset TimeSeriesCollection for plotting 
     * @returns chart to be added to panel 
     */
    private JFreeChart createTimeChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
            chartTitle, "hh:mm:ss", "Vehicles", dataset, true, true, false);
        final XYPlot plot = result.getXYPlot();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setAutoRange(true);
        
        
        // Let's set the series to use the spec's
        // recommended colors.
        XYItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesPaint(0, Color.black);
        renderer.setSeriesPaint(1, Color.blue);
        renderer.setSeriesPaint(2, Color.cyan);
        renderer.setSeriesPaint(3, Color.gray);
        renderer.setSeriesPaint(4, Color.darkGray);
        renderer.setSeriesPaint(5, Color.yellow);
        renderer.setSeriesPaint(6, Color.green);
        renderer.setSeriesPaint(7, Color.red);
        return result;
    }
    
    
    
    /**
     * Helper method to deliver the Chart - currently uses default colours and auto range 
     * @param dataset TimeSeriesCollection for plotting 
     * @returns chart to be added to panel 
     */
    private JFreeChart createBarChart(final CategoryDataset dataset) {
        final JFreeChart result = ChartFactory.createBarChart(
            chartTitle, "", "Number of Vehicles", dataset, PlotOrientation.VERTICAL, true, false, false);
        final CategoryPlot plot = result.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        
        // Let's set the series to use the spec's
        // recommended colors.
        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesPaint(0, Color.blue);
        renderer.setSeriesPaint(1, Color.red);
        return result;
    }



}

