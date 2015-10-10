package com.seebye.xclassesdemo;

import android.graphics.Color;
import android.util.TypedValue;
import android.widget.TextView;

import com.seebye.xclasses.AbstractXClass;
import com.seebye.xclasses.annotations.BeforeOriginalMethod;
import com.seebye.xclasses.annotations.OriginalMethod;
import com.seebye.xclasses.annotations.ParameterManipulation;

import java.util.Calendar;


public class XTextView
	extends AbstractXClass<TextView>
{
	public static String getOriginalClassName()	{ return TextView.class.getCanonicalName();	}

	public XTextView(TextView objectThis) { super(objectThis);	}


	//<editor-fold desc="Our hooks">
	@BeforeOriginalMethod
	private String getHint_Before() throws Throwable
	{
		// this stops the execution of the original class
		// and changes the returned object
		return "changed ;)";
	}

	@ParameterManipulation
	@BeforeOriginalMethod
	private void setText_Before(Object[] aParams,
								CharSequence text, TextView.BufferType type,
								boolean notifyBefore, int oldlen) throws Throwable {
		if(Calendar.getInstance().get(Calendar.SECOND) < 10)
		{
			// show the hint
			// getThis returns the instance of the original class
			// parameter manipulation
			aParams[0] = getThis().getHint();
		}
		else
		{
			// parameter manipulation
			aParams[0] = "hook :) "+getScaledTextSize();
		}
		getThis().setTextColor(Color.RED);// we hooked this method -> it won't be red

		// insteadof getThis() you can also use self() to access the instance of the original class
		self().setTextSize(520, TypedValue.COMPLEX_UNIT_SP);// should be canceled by our hook
	}

	@BeforeOriginalMethod
	private Object setTextSize_Before(int unit, float size) throws Throwable {

		// prevent the execution of the original method
		if(unit > 20)
		{
			return STOP_EXECUTION;
		}

		return CONTINUE_EXECUTION;
	}

	@ParameterManipulation
	@BeforeOriginalMethod
	private void setTextColor_Before(Object[] aParams, int color) throws Throwable {
		/** change the color to green */
		// sorry as java knows no pointers this is the best way to do it as far as i know...
		aParams[0] = Color.GREEN;
	}
	//</editor-fold>


	//<editor-fold desc="methods of the original class which we want to call">

	// this is a hidden method of the class TextView
	// you also need this annotation to be able to call private or protected methods
	@OriginalMethod
	private float getScaledTextSize() { return 0f; }

	//</editor-fold>
}
