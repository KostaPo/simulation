<h1>2D БЕСКОНЕЧНАЯ СИМУЛЯЦИЯ</h1>
Итеративная симуляция 2D мира, населённого травоядными (зеленые) и хищниками (красные). Так же мир содержит ресурсы (траву), которым питаются травоядные, и объекты, с которыми нельзя взаимодействовать (огонь).
2D мир представляет из себя матрицу NxM, каждое существо или объект занимают клетку целиком, нахождение в клетке нескольких объектов/существ - недопустимо.

Каждую итерацию перебирается множество всех обьектов обновляя их состояния.
Состояния травоядных и хищников делятся на четыре части:
<ul>
 <li>IDLE: если на текущий момент итерации невозможно построить маршрут до любой цели по свободным клеткам.</li>
 <li>WALK: перемещение спрайта персонажа из предыдущего положения в текущее.</li>
 <li>ATTACK: атака производится когда персонаж находится в соседней клетке от цели. ***</li>
 <li>DEATH: смерть наступает когда количество HP становится меньше или равно нулю.</li>
</ul>
*** Хищники тоже подвергаются атаке голода. Каждые три шага HP уменьшается.

После полного перебора всех персонажей, обьекты, чей HP меньше или равно нулю перерождаются в случайных клетках.

<h3>Приложение написано на Java в ООП-стиле. В поиске пути используется алгоритм А* (его ортогональная модификация).
Техническое задание проекта: https://zhukovsd.github.io/java-backend-learning-course/Projects/Simulation/</h3>

<img src="https://github.com/KostaPo/simulation/blob/master/target/simulation.png" width="600">

<h1>Запуск приложения:</h1>
<ul>
 <li>Перейдите в папку target</li>
 <li>Запустите программу командой: java -jar simulation-2.0.jar</li>
</ul>
