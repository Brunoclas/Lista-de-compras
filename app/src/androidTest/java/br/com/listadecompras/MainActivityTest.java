package br.com.listadecompras;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import junit.extensions.ActiveTestSuite;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.listadecompras.acitivities.MainActivity;
import br.com.listadecompras.acitivities.ProdutoActivity;
import br.com.listadecompras.zxing.client.android.CaptureActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class, false, true);

    @Test
    public void teste_visualizacao_componentes(){
        onView(withId(R.id.txtInpCodigo)).check(matches(isDisplayed()));
        onView(withId(R.id.btnBuscar)).check(matches(isDisplayed()));
        onView(withId(R.id.fragListProd)).check(matches(isDisplayed()));
        onView(withId(R.id.btnFinalizarCompras)).check(matches(isDisplayed()));
        onView(withId(R.id.btnLerCodigo)).check(matches(isDisplayed()));
        onView(withId(R.id.txtQtdeItem)).check(matches(isDisplayed()));
        onView(withId(R.id.txtVlTotalLista)).check(matches(isDisplayed()));
    }


    @Test
    public void teste_busca_codigo(){
        onView(withId(R.id.txtInpCodigo)).perform(typeText("7891000414002"), closeSoftKeyboard());
        onView(withId(R.id.btnBuscar)).perform(click());
//        onView(withId(R.id.fragListProd)).check(matches(isDisplayed()));
//        onView(withId(R.id.btnFinalizarCompras)).perform(click());
//        onView(withId(R.id.btnLerCodigo)).perform(click());
//        onView(withId(R.id.txtQtdeItem)).perform(typeText(""));
//        onView(withId(R.id.txtVlTotalLista)).perform(typeText(""));
    }

    @Before
    public void setUp() throws Exception{
        Intents.init();
    }

    @Test
    public void teste_chama_activityCadProd(){

        onView(withId(R.id.txtInpCodigo)).perform(typeText("7891000414002"), closeSoftKeyboard());

        Matcher<Intent> matcher = hasComponent(ProdutoActivity.class.getName());

        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, null);
        intending(matcher).respondWith(result);

        onView(withId(R.id.btnBuscar)).perform(click());

        intended(matcher);

    }

    @After
    public void tearDown() throws Exception{
        Intents.release();
    }
}
