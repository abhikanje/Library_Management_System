package com.coderabhi.LibraryManagementSystem1.Service;

import com.coderabhi.LibraryManagementSystem1.Dto.IssueBookRequestDto;
import com.coderabhi.LibraryManagementSystem1.Dto.IssueBookResponseDto;
import com.coderabhi.LibraryManagementSystem1.Entity.Book;
import com.coderabhi.LibraryManagementSystem1.Entity.LibraryCard;
import com.coderabhi.LibraryManagementSystem1.Entity.Transaction;
import com.coderabhi.LibraryManagementSystem1.Enum.CardStatus;
import com.coderabhi.LibraryManagementSystem1.Enum.TransactionStatus;
import com.coderabhi.LibraryManagementSystem1.Repository.BookRepository;
import com.coderabhi.LibraryManagementSystem1.Repository.CardRepository;
import com.coderabhi.LibraryManagementSystem1.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    BookRepository bookRepository;

    @Autowired
    private JavaMailSender emailSender;

    public IssueBookResponseDto issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception {

        // Create Transaction Object
        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(String.valueOf(UUID.randomUUID()));
        transaction.setIssueOperation(true);

        // 1 Step
        LibraryCard card;
        try{
            card = cardRepository.findById(issueBookRequestDto.getCardId()).get();
        }
        catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid card id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid card id");
        }

        Book book;
        try{
            book = bookRepository.findById(issueBookRequestDto.getBookId()).get();
        }
        catch(Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Invalid Book Id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid Book Id");
        }

        // both and card and book are valid
        transaction.setBook(book);
        transaction.setCard(card);

        if(card.getStatus()!= CardStatus.ACTIVATED){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Your card is not activated");
            transactionRepository.save(transaction);
            throw new Exception("Your card is not activated");
        }

        if(book.isIssued()==true){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Sorry! Book is already issued.");
            transactionRepository.save(transaction);
            throw new Exception("Sorry! Book is already issued.");
        }

        // I can issue the book
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setMessage("Transaction was succesfull");

        book.setIssued(true);
        book.setCard(card);
        book.getTransactions().add(transaction);
        card.getTransactions().add(transaction);
        card.getBooksIssued().add(book);

        cardRepository.save(card);  // will save book and tranaction also

        // Prepare Response Dto
        IssueBookResponseDto issueBookResponseDto = new IssueBookResponseDto();
        issueBookResponseDto.setTransanctionId(transaction.getTransactionNumber());
        issueBookResponseDto.setTransactionStatus(TransactionStatus.SUCCESS);
        issueBookResponseDto.setBookName(book.getName());

        // send an email
        String text = "Congrats !!." + card.getStudent().getName()+ "You have been issued "+book.getTitle()+" book.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("backendavengers@gmail.com");
        message.setTo(card.getStudent().getEmail());
        message.setSubject("Issue Book Notification");
        message.setText(text);
        emailSender.send(message);

        return issueBookResponseDto;

    }

    public String getAllTxns(int cardId){
        List<Transaction> transactionList = transactionRepository.getAllSuccessfullTxnsWithCardNo(cardId);
        String ans = "";
        for(Transaction t: transactionList){
            ans += t.getTransactionNumber();
            ans += "\n";
        }

        return ans;
    }
}
