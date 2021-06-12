import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import javax.naming.ldap.Control;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryOrderFormTest {
    @Test
    void enteringValidData() { // ввод валидных значений.
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Хантер Томпсон");
        $("[data-test-id=phone] input").setValue("+79008007060");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15));


    }

    @Test
    void entering_a_DoubleLastName() { // ввод фамилии через дефис
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Хантер Томснон-Рабинович");
        $("[data-test-id=phone] input").setValue("+79008007060");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15));


    }

    @Test
    void thereIsNoMarkInTheCheckBox() { // не стоит отметка в чекбоксе
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Федор Достоевский");
        $("[data-test-id=phone] input").setValue("+79008007060");
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .checkbox__text").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void cityFieldIsNotFilled() { //не заполнено поле "город"
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        open("http://localhost:9999");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Федор Достоевский");
        $("[data-test-id=phone] input").setValue("+79008007060");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));

    }

    @Test
    void wrongDateEntered() { // не верно указана дата
        String date = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Хантер Томпсон");
        $("[data-test-id=phone] input").setValue("+79008007060");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Заказ на выбранную дату невозможен"));


    }

    @Test
    void fieldNameAndSurnameIsNotFilled() { //не заполнено поле имя и фамилия
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=phone] input").setValue("+79008007060");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));


    }

    @Test
    void phoneNumberIsOneDigitLonger() { // номер телефона длиннее на одну цифру
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Хантер Томпсон");
        $("[data-test-id=phone] input").setValue("+790080070601");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678"));


    }

}
