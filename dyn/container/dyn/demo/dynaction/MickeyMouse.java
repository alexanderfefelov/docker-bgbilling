package demo.dynaction;

import bitel.billing.server.ActionBase;
import org.w3c.dom.Element;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*

Вызов:

GET/POST /bgbilling/executer?user=admin&pswd=admin&module=demo&action=MickeyMouse

*/

public class MickeyMouse extends ActionBase {

    @Override
    public void doAction() throws Exception {
        final Map<String, String> DATA = Collections.unmodifiableMap(new HashMap<String, String>() {{
            put("Afrikaans", "Mickie");
            put("Bulgarian", "Miki Maus");
            put("Chinese (Mandarin)", "Mi Lao Shu");
            put("Chinese (Cantonese)", "Mai Kay Shiu Shu");
            put("Esperanto", "Micjo Muso");
            put("Estonian", "Mikki Hiir");
            put("Finnish", "Mikki Hiiri");
            put("German", "Micky Maus");
            put("Greek", "Mikki Maous/Miky Maoye");
            put("Hungarian", "Miki Eger");
            put("Icelandic", "Mikki Mus");
            put("Indonesian", "Miki Tikus");
            put("Italian", "Topolino");
            put("Japanese", "Mickey Ma-u-su");
            put("Latin", "Michael Muulus");
            put("Lithuanian", "Peliukas Mikis");
            put("Malaysian", "Miki Tikus");
            put("Norwegian", "Mikke Mus");
            put("Polish", "Miki");
            put("Portuguese", "Rato Mickey");
            put("Rumanian", "Miki Maus");
            put("Russian", "Mikki Maus");
            put("Serbo-Croatian", "Ujka Miki");
            put("Spanish", "El Raton Miguelito");
            put("Slovenian", "Miki Miška");
            put("Swedish", "Musse Pigg");
            put("Turkish", "Miki");
            put("Vietnamese", "Mick-Kay");
            put("Yugsoslavian", "Miki Maus");
        }});

        Element mickeyMouse = createElement(rootNode, "mickeyMouse");
        for (Map.Entry entry : DATA.entrySet()) {
            String k = entry.getKey().toString();
            String v = entry.getValue().toString();
            addListItem(mickeyMouse, k, v);
        }

        // Ответ:
        //   <?xml version="1.0" encoding="UTF-8"?>
        //   <data status="ok">
        //     <mickeyMouse>
        //       <item id="Italian" title="Topolino"/>
        //       <item id="Russian" title="Mikki Maus"/>
        //       ...
        //       <item id="Rumanian" title="Miki Maus"/>
        //      <item id="Greek" title="Mikki Maous/Miky Maoye"/>
        //    </mickeyMouse>
        //  </data>
    }

}
