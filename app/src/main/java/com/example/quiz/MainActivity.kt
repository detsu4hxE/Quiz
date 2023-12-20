package com.example.quiz

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val RiddleList: List<Riddle> = listOf(
        Riddle("Стоит дуб. В нём 12 гнёзд. В каждом гнезде по 4 яйца, в каждом яйце по 7 цыплят.", setOf<String>("Год", "Дом", "Неделя", "Дуб")),
        Riddle("В углу сито, не руками вито.", setOf<String>("Паутина", "Корзина", "Чайник", "Дом")),
        Riddle("Кто над нами вверх ногами?", setOf<String>("Муха", "Дом", "Лиса", "Краб")),
        Riddle("На раскрашенных страницах много праздников хранится.", setOf<String>("Календарь", "Дом", "Страницы", "Схрон")),
        Riddle("В лесу без огня котёл кипит.", setOf<String>("Муравейник", "Дом", "Чайник", "Чулан")),
        Riddle("Шёл мужик по лесу, нёс зеркало за поясом. Лесу поклонился, лес повалился.", setOf<String>("Топор", "Дом", "Крот", "Тарелка")),
        Riddle("Меня легко найдёшь зимой, но гибну я всегда весной; расту я корнем вверх — вниз головой.", setOf<String>("Сосулька", "Дом", "Квадрат", "Стержень")),
        Riddle("У кого глаза на рогах, а дом на спине?", setOf<String>("Улитка", "Дом", "Рак", "Ступень")),
        Riddle("Бел, да не сахар. Пушист, да не птица. Нет ног, а идёт.", setOf<String>("Снег", "Сахар", "Дом", "Свист")),
        Riddle("Не мотор, а гудит. Не пилот, а летит. Не змея, а жалит. Не воин, а врага валит.", setOf<String>("Оса", "Мёд", "Дом", "Иголка")),
        Riddle("Дом — не дом. Из трубы — дым столбом. Весь он ходит ходуном. И качается народ взад-вперёд.", setOf<String>("Пароход", "Дом", "Камин", "Костёр")),
        Riddle("По сеням взад-вперёд ходит, а в избу не входит.", setOf<String>("Дверь", "Дом", "Окно", "Труба")),
        Riddle("Какие животные хвойные?", setOf<String>("Ежи", "Лисы", "Змеи", "Медведи")),
        Riddle("Шарик невелик, да плакать велит.", setOf<String>("Лук", "Морковь", "Чеснок", "Миг")),
        Riddle("Соль, а не солёная, фасоль, а не зелёная.", setOf<String>("Ноты", "Пианино", "Дом", "Сок")),
        Riddle("Всем, кто придёт, и всем, кто уйдёт, руку подаёт.", setOf<String>("Ручка", "Дом", "Лиса", "Крот")),
        Riddle("Сам верхом, а ноги за ушами.", setOf<String>("Очки", "Муравьед", "Дом", "Камин")),
        Riddle("Ходоки на весь свет, а ног своих нет.", setOf<String>("Ботинки", "Волки", "Гуси", "Лисы")),
        Riddle("Ходят великаны, горбят океаны. К берегу придут — сразу пропадут.", setOf<String>("Волны", "Море", "Киты", "Слоны")),
        Riddle("Колоб-колобок забрёл на потолок.", setOf<String>("Солнце", "Заяц", "Мёд", "Пчела")),
        Riddle("При двух руках, с одной ходит по воде. На себе носит, а не тонет.", setOf<String>("Лодка", "Тарелка", "Тазик", "Дом")),
        Riddle("Две сестры качались, правды добивались. Правды добились — остановились.", setOf<String>("Весы", "Суд", "Пряности", "Волк")),
        Riddle("Не бранит никто мальца, но колотят без конца. Пока он не скроется, никто не успокоится.", setOf<String>("Гвоздь", "Шуруп", "Младенец", "Крот")),
        Riddle("Какой чудачок имел пятачок, его не потратил и другого не нажил?", setOf<String>("Поросёнок", "Телёнок", "Ягнёнок", "Дом")),
        Riddle("Грамоты не знаю, целый век пишу.", setOf<String>("Перо", "Нож", "Крот", "Дом")),
        Riddle("Молод был — молодцом глядел; под старость устал, меркнуть стал. Новый родится — опять развеселится.", setOf<String>("Месяц", "Свин", "Дом", "День")),
        Riddle("Летит — пищит. Сядет — замолчит.", setOf<String>("Комар", "Молния", "Дом", "Лягушка")),
        Riddle("Шар невелик, лениться не велит; если знаешь предмет, то покажешь весь свет.", setOf<String>("Глобус", "Земля", "Мяч", "Дом")),
        Riddle("А утром выйдет солнышко — и не найти ни зёрнышка!", setOf<String>("Звёзды", "Гвозди", "Искры", "Вафли")),
        Riddle("Вот так дом: одно окно. Каждый день в окне кино!", setOf<String>("Телевизор", "Театр", "Дверь", "Дом"))
    )

    var GameList = (RiddleList.shuffled()).take(10);
    var gameInProgress = false;
    var answeredTotal = 0;
    var answeredCorrect = 0;
    var correctAnswer = 0;
    var questionAnswered = 0;
    var userAnswer = "";

    lateinit var binding: ActivityMainBinding
    private var launcher: ActivityResultLauncher<Intent>? = null;
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);
        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val answer = result.data?.getStringExtra("chosenAnswer")
                    var corr = "Неправильный ответ"
                    binding.textUserAnswer.setVisibility(View.VISIBLE);
                    binding.textAnswerCorrectness.setVisibility(View.VISIBLE);
                    binding.textAnswerCorrectness.setTextColor(Color.RED)
                    if (answer == GameList.get(questionAnswered).answers.elementAtOrNull(0).toString()) {
                        corr = "Правильный ответ";
                        binding.textAnswerCorrectness.setTextColor(Color.GREEN)
                        answeredCorrect++;
                    }
                    val qText = GameList.get(questionAnswered).riddleText;
                    questionAnswered++;
                    if (questionAnswered == 10)
                    {
                        binding.buttonInfo.isEnabled = true;
                        gameInProgress = false;
                        answeredTotal += questionAnswered;
                        questionAnswered = 0;
                        binding.buttonQuestion.text = "Начать";
                    }
                    binding.buttonQuestion.isEnabled = true;
                    binding.buttonAnswer.isEnabled = false;
                    binding.textQuest.text = qText;
                    binding.textUserAnswer.text = "Ваш ответ: " + answer;
                    binding.textAnswerCorrectness.text = corr;
                }
            }
    }



    fun btnClickNext(view: View) {
        binding.textUserAnswer.setVisibility(View.GONE);
        binding.textAnswerCorrectness.setVisibility(View.GONE);
        if (gameInProgress) {
            binding.textQuest.text = GameList.get(questionAnswered).riddleText
            binding.textView.text = "Вопрос " + (questionAnswered+1)
            binding.buttonAnswer.isEnabled = true
            binding.buttonQuestion.isEnabled = false
            binding.buttonQuestion.text = "Загадка"
        }
        else {
            GameList = (RiddleList.shuffled()).take(10)
            binding.textQuest.text = GameList.get(questionAnswered).riddleText
            binding.buttonAnswer.isEnabled = true
            binding.buttonInfo.isEnabled = false
            gameInProgress = true
            binding.textView.text = "Вопрос 1"
            questionAnswered = 0
            answeredCorrect = 0
        }
    }

    fun btnClickAnswer(view: View) {
        val intent = Intent(this, ActivityAnswer::class.java);
        intent.putExtra("question", GameList[questionAnswered].riddleText);
        intent.putExtra("answerCorrect", GameList[questionAnswered].answers.elementAtOrNull(0));
        intent.putExtra("answer1", GameList[questionAnswered].answers.elementAtOrNull(1));
        intent.putExtra("answer2", GameList[questionAnswered].answers.elementAtOrNull(2));
        intent.putExtra("answer3", GameList[questionAnswered].answers.elementAtOrNull(3));
        launcher?.launch(intent);
    }

    fun btnClickInfo(view: View) {
        val intent2 = Intent(this, ActivityInfo::class.java);
        intent2.putExtra("answeredTotal", answeredTotal.toString());
        intent2.putExtra("answeredCorrect", answeredCorrect.toString());
        intent2.putExtra("answeredWrong", (answeredTotal - answeredCorrect).toString());
        startActivity(intent2);
    }
}

class Riddle(
    val riddleText: String,
    var answers:Set<String>) {
}