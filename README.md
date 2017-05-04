# DesafioMobile-Reto3

Mi solución del reto 3 de www.belatrixsf.com/desafio-mobile-expert/

![Interfaz gráfica de la aplicación](https://raw.githubusercontent.com/hugoangeles0810/DesafioMobile-Reto3/master/art/app.gif)


## Como agregarlo a tu diseño
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

## Manejo del click
Para implementar un click listener a cada **BubbleView** puede establecer un **BubbleGroup.OnBubbleClickListener** en la instancia del **BubbleGroup**, también puede establecer un **View.OnClickListener** independiente a cada **BubbleView**, tenga en cuenta que estos listeners se pueden solapar.

## Desarrollado Por
* Hugo Angeles  - <hugoangeles0810@gmail.com>

## Licencia

    MIT License

    Copyright (c) 2017 Hugo Angeles Chavez

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.

