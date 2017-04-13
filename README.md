# DesafioMobile-Reto3

Mi solución del reto 3 de www.belatrixsf.com/desafio-mobile-expert/

![Interfaz gráfica de la aplicación](https://raw.githubusercontent.com/hugoangeles0810/DesafioMobile-Reto3/master/art/app.gif)

### Como agregarlo a tu diseño
```xml
<io.github.hugoangeles0810.desafiomobile_reto3.bubble.BubbleGroup
        android:id="@+id/bubble_group"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:bubble_control_text="A"
        app:bubble_control_size="100dp"
        app:bubble_control_radio="120dp">

        <io.github.hugoangeles0810.desafiomobile_reto3.bubble.BubbleView
            android:layout_width="50dp"
            android:layout_height="50dp"
            app:bubble_background="#995688"
            android:textSize="12sp"
            android:text="Item 1"/>

        <io.github.hugoangeles0810.desafiomobile_reto3.bubble.BubbleView
            android:layout_width="50dp"
            android:layout_height="50dp"
            app:bubble_background="#549970"
            android:textSize="12sp"
            android:text="Item 2"/>

        ...

    </io.github.hugoangeles0810.desafiomobile_reto3.bubble.BubbleGroup>
```
