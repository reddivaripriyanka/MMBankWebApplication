package com.capgemini.training.webapplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moneymoney.account.SavingsAccount;
import com.moneymoney.account.service.SavingsAccountService;
import com.moneymoney.account.service.SavingsAccountServiceImpl;
import com.moneymoney.account.util.DBUtil;
import com.moneymoney.exception.AccountNotFoundException;

/**
 * Servlet implementation class controller
 */
@WebServlet("*.mm")
public class controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	public void init() throws ServletException {
		super.init();
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = DriverManager.getConnection
						("jdbc:mysql://localhost:3306/bankapp_db", "root", "root");
				PreparedStatement preparedStatement = 
						connection.prepareStatement("DELETE FROM ACCOUNT");
				preparedStatement.execute();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path=request.getServletPath();
		SavingsAccountService savingsAccountService=new SavingsAccountServiceImpl();
		PrintWriter out=response.getWriter();
		switch(path){
		case "/addAccount.mm":
			response.sendRedirect("AddAccount.html");
			break;
		case "/createAccount.mm":
			String name=request.getParameter("aHolderName");
			double amount=Double.parseDouble(request.getParameter("accountBalance"));
			boolean salaried =request.getParameter("salary").equalsIgnoreCase("n")?false:true;
				try {
					savingsAccountService.createNewAccount(name, amount, salaried);
					response.sendRedirect("index.html");
				} catch (ClassNotFoundException  | SQLException e) {
					e.printStackTrace();
				}
				
				break;
		case "/closeAccount.mm":
			response.sendRedirect("CloseAccount.html");
			break;
		case "/deleteAccount.mm":
			int accountNumber=Integer.parseInt(request.getParameter("aNumber"));
			try {
				try {
					boolean result=savingsAccountService.deleteAccount(accountNumber);
					
					if(result==true)
						System.out.println("Succesfully Close Account");
					else
						System.out.println("Not update");
					response.sendRedirect("index.html");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (AccountNotFoundException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			break;

		case "/withdrawAmount.mm":
			response.sendRedirect("Withdraw.html");
			break;
		case "/withdrawAmountInAccount.mm":
			int accNumber=Integer.parseInt(request.getParameter("aNumber"));
			double withdrawAmount=Double.parseDouble(request.getParameter("wBalance"));
			SavingsAccount savingsAccount = null;
			try {
				savingsAccount = savingsAccountService.getAccountById(accNumber);
				savingsAccountService.withdraw(savingsAccount, withdrawAmount);
				response.sendRedirect("index.html");
				DBUtil.commit();
			} catch (ClassNotFoundException | SQLException | AccountNotFoundException e) {
				try {
					DBUtil.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (Exception e) {
				try {
					DBUtil.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			break;
		case "/depositAmount.mm":
			response.sendRedirect("Deposit.html");
			break;
		case "/depositAmountInAccount.mm":
			int aNumber=Integer.parseInt(request.getParameter("accNumber"));
			double dAmount=Double.parseDouble(request.getParameter("dBalance"));
			SavingsAccount savingAccount = null;
			try {
				savingAccount = savingsAccountService.getAccountById(aNumber);
				savingsAccountService.deposit(savingAccount, dAmount);
				response.sendRedirect("index.html");
				DBUtil.commit();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				try {
					DBUtil.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} catch (Exception e) {
				try {
					DBUtil.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			break;
		case "/fundTransfer.mm":
			response.sendRedirect("fundTransfer.html");
			break;
			
		case "/fundTrans.mm":
			int senderAccountNumber=Integer.parseInt(request.getParameter("saccNumber"));
			int receiverAccountNumber=Integer.parseInt(request.getParameter("raccNumber"));
			double transferAmount=Double.parseDouble(request.getParameter("tAmount"));
			try {
				SavingsAccount senderSavingsAccount = savingsAccountService.getAccountById(senderAccountNumber);
				SavingsAccount receiverSavingsAccount = savingsAccountService.getAccountById(receiverAccountNumber);
				savingsAccountService.fundTransfer(senderSavingsAccount, receiverSavingsAccount, transferAmount);
				response.sendRedirect("index.html");
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "/getCurreneBalance.mm":
			response.sendRedirect("CurrentBalance.html");
			break;
		case "/checkCurrentBalance.mm":
			int accBalance=Integer.parseInt(request.getParameter("aNumber"));
			try {
				double balance=savingsAccountService.checkCurrentBalance(accBalance);
				out.println("Available balance is"+balance);
			} catch (ClassNotFoundException | SQLException
					| AccountNotFoundException e) {
				e.printStackTrace();
			}
			
			
		}
			
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
