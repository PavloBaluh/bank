package com.bank.bank.Service;

import com.bank.bank.Dao.BankomatDao;
import com.bank.bank.Models.Bankomat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BankomatService {
    @Autowired
    BankomatDao bankomatDao;


    public void addNotes(int[] notes) {
        Bankomat bankomat = bankomatDao.getOne(1);
        for (int note : notes) {
            if(note == 100){
                bankomat.setNote100(bankomat.getNote100()+1);
            }
            if(note == 200){
                bankomat.setNote200(bankomat.getNote200()+1);
            }
            if(note == 500){
                bankomat.setNote500(bankomat.getNote500()+1);
            }
        }
        bankomatDao.save(bankomat);
    }
    public ArrayList<Integer> GetMoney(int sum){
        ArrayList<Integer> returnsNotes= new ArrayList<>();
        Bankomat bankomat = bankomatDao.getOne(1);
        int fiveHundred = 0;
        int hundred;
        int twoHundred;
        if (sum < 500){
            twoHundred = sum/200;
            hundred = (sum%200) /100;
        }
        else {
            fiveHundred = sum /500;
            twoHundred = (sum %500)/200;
            hundred = ((sum %500)%200) /100;
        }

        if (fiveHundred != 0) {
            int newQuantity = bankomat.getNote500() - fiveHundred;
            if (newQuantity>=0){
                bankomat.setNote500(newQuantity);
                for (int i = 1; i <= fiveHundred; i++) {
                    returnsNotes.add(500);
                }
            }
            else {
                int note500 = bankomat.getNote500();
                int needed = 500*(fiveHundred - note500);
                twoHundred += needed/200;
                hundred += (needed%200) /100;
                for (int i = 1; i <= note500; i++) {
                    returnsNotes.add(500);
                }
                bankomat.setNote500(0);

            }
        }
        if (twoHundred != 0){
            int newQuantity = bankomat.getNote200() - twoHundred;
            if (newQuantity>=0){
                bankomat.setNote200(newQuantity);
                for (int i = 1; i <= twoHundred; i++) {
                    returnsNotes.add(200);
                }
            }
            else {
                int note200 = bankomat.getNote200();
                int needed = 200*(twoHundred - note200);
                hundred += needed /100;
                for (int i = 1; i <= note200; i++) {
                    returnsNotes.add(500);
                }

            }
        }
        if (hundred !=0) {
            int newQuantity = bankomat.getNote100() - hundred;
            if (newQuantity >= 0) {
                bankomat.setNote100(newQuantity);
                for (int i = 1; i <= hundred; i++) {
                    returnsNotes.add(100);
                }
            }
            else {
                return null;
            }
        }

        bankomatDao.save(bankomat);
        return returnsNotes;
    }
}
