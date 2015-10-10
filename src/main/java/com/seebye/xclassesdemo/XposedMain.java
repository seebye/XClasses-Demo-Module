package com.seebye.xclassesdemo;

import com.seebye.xclasses.exceptions.HookOverflowException;
import com.seebye.xclasses.exceptions.OriginalClassNameMissingException;
import com.seebye.xclasses.exceptions.ParameterManipulationAfterException;
import com.seebye.xclasses.exceptions.ParameterManipulationBeforeException;
import com.seebye.xclasses.exceptions.PrivateException;
import com.seebye.xclasses.exceptions.StaticNonStaticException;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static com.seebye.xclasses.utils.XposedUtils.hook;
import static com.seebye.xclasses.utils.XposedUtils.setup;

/**
 * Created by nico on 10.10.15.
 */
public class XposedMain implements IXposedHookLoadPackage
{
	@Override
	public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam)
			throws Throwable {
		setup(lpparam.classLoader);

		try
		{
			switch (lpparam.packageName)
			{
				case "com.android.systemui":
					handleSystemUI();
					break;
			}
		}
		catch (OriginalClassNameMissingException e) {
			e.printStackTrace();
		} catch (ParameterManipulationAfterException e) {
			e.printStackTrace();
		} catch (StaticNonStaticException e) {
			e.printStackTrace();
		} catch (ParameterManipulationBeforeException e) {
			e.printStackTrace();
		} catch (PrivateException e) {
			e.printStackTrace();
		} catch (HookOverflowException e) {
			e.printStackTrace();
		}
	}

	private void handleSystemUI()
			throws PrivateException, ParameterManipulationAfterException, StaticNonStaticException,
				   OriginalClassNameMissingException, HookOverflowException,
				   ParameterManipulationBeforeException {
		hook(XTextView.class);
	}
}
