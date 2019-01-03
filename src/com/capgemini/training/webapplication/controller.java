package com.capgemini.training.webapplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.RequestDispatcher;
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
	boolean flag = false;
	private RequestDispatcher dispatcher;
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/bankapp_db", "root", "root");
			PreparedStatement preparedStatement = connection
					.prepareStatement("DELETE FROM ACCOUNT");
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
		int accountNumber=0;
		switch(path){
		case "/addAccount.mm":
			response.sendRedirect("AddAccount.jsp");
			break;
		case "/createAccount.mm":
			String name=request.getParameter("aHolderName");
			double amount=Double.parseDouble(request.getParameter("accountBalance"));
			boolean salaried =request.getParameter("salary").equalsIgnoreCase("n")?false:true;
				try {
					savingsAccountService.createNewAccount(name, amount, salaried);
					response.sendRedirect("getAll.mm");
				} catch (ClassNotFoundException  | SQLException e) {
					e.printStackTrace();
				}
				
				break;
		case "/closeAccount.mm":
			response.sendRedirect("CloseAccount.jsp");
			break;
		case "/deleteAccount.mm":
			accountNumber=Integer.parseInt(request.getParameter("aNumber"));
			try {
				try {
					boolean result=savingsAccountService.deleteAccount(accountNumber);
					if(result==true)
						System.out.println("Succesfully Close Account");
					else
						System.out.println("Not update");
					response.sendRedirect("getAll.mm");
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
			response.sendRedirect("Withdraw.jsp");
			break;
		case "/withdrawAmountInAccount.mm":
			int accNumber=Integer.parseInt(request.getParameter("aNumber"));
			double withdrawAmount=Double.parseDouble(request.getParameter("wBalance"));
			SavingsAccount savingsAccount = null;
			try {
				savingsAccount = savingsAccountService.getAccountById(accNumber);
				savingsAccountService.withdraw(savingsAccount, withdrawAmount);
				response.sendRedirect("getAll.mm");
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
			response.sendRedirect("Deposit.jsp");
			break;
		case "/depositAmountInAccount.mm":
			int aNumber=Integer.parseInt(request.getParameter("accNumber"));
			double dAmount=Double.parseDouble(request.getParameter("dBalance"));
			SavingsAccount savingAccount = null;
			try {
				savingAccount = savingsAccountService.getAccountById(aNumber);
				savingsAccountService.deposit(savingAccount, dAmount);
				response.sendRedirect("getAll.mm");
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
			response.sendRedirect("fundTransfer.jsp");
			break;
			
		case "/fundTrans.mm":
			int senderAccountNumber=Integer.parseInt(request.getParameter("saccNumber"));
			int receiverAccountNumber=Integer.parseInt(request.getParameter("raccNumber"));
			double transferAmount=Double.parseDouble(request.getParameter("tAmount"));
			try {
				SavingsAccount senderSavingsAccount = savingsAccountService.getAccountById(senderAccountNumber);
				SavingsAccount receiverSavingsAccount = savingsAccountService.getAccountById(receiverAccountNumber);
				savingsAccountService.fundTransfer(senderSavingsAccount, receiverSavingsAccount, transferAmount);
				response.sendRedirect("getAll.mm");
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "/getCurreneBalance.mm":
			response.sendRedirect("CurrentBalance.jsp");
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
			
			break;
		case "/searchForm.mm":
			response.sendRedirect("SearchForm.jsp");
			break;
		case "/search.mm":
			accountNumber = Integer.parseInt(request.getParameter("txtAccountNumber"));
			try {
				SavingsAccount account = savingsAccountService.getAccountById(accountNumber);
				request.setAttribute("account", account);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException | AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
	case "/getAll.mm":
		try {
			List<SavingsAccount> accounts =savingsAccountService.getAllSavingsAccount();
			//savingsAccounts = savingsAccountService.getAllSavingsAccount();
			request.setAttribute("accounts", accounts);
			dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
			dispatcher.forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		break;
	case "/sortByName.mm":
			flag=!flag;
 				ArrayList<SavingsAccount> accounts= new ArrayList<SavingsAccount>();
			try {
				accounts = (ArrayList<SavingsAccount>) savingsAccountService.getAllSavingsAccount();
				Collections.sort(accounts , new Comparator<SavingsAccount>()
						{
							@Override
							public int compare(SavingsAccount arg0, SavingsAccount arg1) {
								int result=arg0.getBankAccount().getAccountHolderName().compareTo
										(arg1.getBankAccount().getAccountHolderName());
								if(flag==true)
									return result;
								else
									return -result;
					}
						});
						request.setAttribute("accounts", accounts);
						dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
						dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			 
			break;
	case "/sortByBalance.mm":
		flag=!flag;
				ArrayList<SavingsAccount> accountsList= new ArrayList<SavingsAccount>();
		try {
			accountsList = (ArrayList<SavingsAccount>) savingsAccountService.getAllSavingsAccount();
			Collections.sort(accountsList , new Comparator<SavingsAccount>()
					{
						@Override
						public int compare(SavingsAccount arg0, SavingsAccount arg1) {
							int result=(int) (arg0.getBankAccount().getAccountBalance()-
									(arg1.getBankAccount().getAccountBalance()));
							if(flag==true)
								return result;
							else
								return -result;
				}
					});
					request.setAttribute("accounts", accountsList);
					dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
					dispatcher.forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		 
		break;
	case "/sortByAccountNumber.mm":
		flag=!flag;
				ArrayList<SavingsAccount> accountList= new ArrayList<SavingsAccount>();
		try {
			accountList = (ArrayList<SavingsAccount>) savingsAccountService.getAllSavingsAccount();
			Collections.sort(accountList , new Comparator<SavingsAccount>()
					{
						@Override
						public int compare(SavingsAccount arg0, SavingsAccount arg1) {
							int result=(int) (arg0.getBankAccount().getAccountNumber()-
									(arg1.getBankAccount().getAccountNumber()));
							if(flag==true)
								return result;
							else
								return -result;
				}
					});
					request.setAttribute("accounts", accountList);
					dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
					dispatcher.forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		 
		break;
	case "/sortByTypeOfSalary.mm":
		flag=!flag;
				ArrayList<SavingsAccount> account= new ArrayList<SavingsAccount>();
		try {
			account = (ArrayList<SavingsAccount>) savingsAccountService.getAllSavingsAccount();
			Collections.sort(account , new Comparator<SavingsAccount>()
					{
						@Override
						public int compare(SavingsAccount arg0, SavingsAccount arg1) {
							int result=Boolean.compare(arg0.isSalary(), arg1.isSalary());
							if(flag==true)
								return result;
							else
								return -result;
				}
					});
					request.setAttribute("accounts", account);
					dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
					dispatcher.forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		 
		break;
	case "/updateAccount.mm":
		response.sendRedirect("Update.jsp");
		break;
	case "/update.mm":
		int accountBal = Integer.parseInt(request.getParameter("aNumber"));
		try {
			SavingsAccount accountUpdate = savingsAccountService.getAccountById(accountBal);
			request.setAttribute("accounts", accountUpdate);
			dispatcher = request.getRequestDispatcher("UpdateDetails.jsp");
			dispatcher.forward(request, response);
		} catch (ClassNotFoundException | SQLException| AccountNotFoundException e) {
			e.printStackTrace();
		}
			break;
	case "/updateAcc.mm":
		int accountId = Integer.parseInt(request.getParameter("txtNum"));
		SavingsAccount accountUpdate;
		try {
			accountUpdate = savingsAccountService.getAccountById(accountId);
			String accHName = request.getParameter("txtAccHn");
			accountUpdate.getBankAccount().setAccountHolderName(accHName);
			double accBal = Double.parseDouble(request.getParameter("txtBal"));
			boolean isSalary = request.getParameter("rdSal").equalsIgnoreCase("no")?false:true;
			accountUpdate.setSalary(isSalary);
			savingsAccountService.updateAccount(accountUpdate);
			response.sendRedirect("getAll.mm");
		} catch (ClassNotFoundException | SQLException
				| AccountNotFoundException e) {
			e.printStackTrace();
		}
			break;
	}	
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
