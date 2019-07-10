package com.bank.bank.Controllers;

import com.bank.bank.Models.Bank_account;
import com.bank.bank.Models.Bankomat;
import com.bank.bank.Models.User;
import com.bank.bank.Service.Bank_AccountService;
import com.bank.bank.Service.BankomatService;
import com.bank.bank.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@AllArgsConstructor
public class MainController {
    private UserService userService;
    private Bank_AccountService accountService;
    private PasswordEncoder encoder;
    private BankomatService bankomatService;

    @PostMapping("/register")
    public void register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        Bank_account bank_account = new Bank_account();
        bank_account.setMoney(0);
        bank_account.setCardNumber((long)(Math.random() * 10000000));
        user.setBank_account(bank_account);
        accountService.save(bank_account);
        userService.save(user);
    }

    @PostMapping("/PutMoney")
    public String PutMoney(@RequestParam("sum") int[] notes) {
        int sum = 0;
        for (int currentNote : notes) {
            System.out.println(currentNote);
            if (currentNote % 100 != 0 && currentNote % 200 != 0 && currentNote % 500 != 0) {
                return "Купюри не кратні 100 200 500";
            }
            else sum += currentNote;
        }
        bankomatService.addNotes(notes);
        User currentUser = getCurrentUser();
        Bank_account bank_account = currentUser.getBank_account();
        bank_account.setMoney(bank_account.getMoney() + sum);
        accountService.save(bank_account);
        return "Рахунок = " + bank_account.getMoney();
    }

    @PostMapping("/GetMoney")
    public String GetMoney(@RequestParam("sum") int sum) {

        if (sum % 100 == 0 || sum % 200 == 0 || sum % 500 == 0) {
            User currentUser = getCurrentUser();
            Bank_account bank_account = currentUser.getBank_account();
            double money = bank_account.getMoney();
            double res = money - sum;
            if (res >= 0) {
                ArrayList<Integer> returnMoney = bankomatService.GetMoney(sum);
                if (returnMoney == null){
                    return "В банкоматі не достатньо купюр";
                }
                bank_account.setMoney(res);
                accountService.save(bank_account);
                return "Рахунок = " + bank_account.getMoney() + " Кошти:" + returnMoney ;
            }
            return "Не достатньо коштів";
        }
        return "Сума не кратна 100 200 500";
    }

    @PostMapping("/TransferMoney")
    public String TransferMoney (@RequestParam("sum") int sum, @RequestParam("curdNumber") int curdNumber){
        User currentUser = getCurrentUser();
        if (currentUser.getBank_account().getMoney() - sum >=0) {
            currentUser.getBank_account().setMoney(currentUser.getBank_account().getMoney() - sum);
            Bank_account byCurdNumber = accountService.getByCurdNumber(curdNumber);
            byCurdNumber.setMoney(byCurdNumber.getMoney() + sum);
            accountService.save(byCurdNumber);
            accountService.save(currentUser.getBank_account());
            return "На рахунок" + curdNumber + "Переведено" + sum;
        }
        return "Не достатньо коштів";
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return userService.findByName(name);
    }

}
