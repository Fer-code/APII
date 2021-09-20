package com.example.api;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.util.Date;

public class ObrasWidg extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            //Intent para ir para a classe artista
            Intent intent = new Intent(context, Artista.class);
            //Intent para ir para a classe exposicao
            Intent intentdois = new Intent(context, Exposicao.class);

            //Primeira intent pendente
            PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, intent, 0);

            //Segunda intent pendente
            PendingIntent pend= PendingIntent.getActivity(context, 1, intentdois, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.obras_widg);

            //Ao clicar em artista ou exposição vai chamar a intent pendente
            views.setOnClickPendingIntent(R.id.button_update, pendingIntent);
            views.setOnClickPendingIntent(R.id.button_expo, pend);


            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

}
