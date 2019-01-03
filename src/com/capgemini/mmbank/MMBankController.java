package com.capgemini.mmbank;

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

@WebServlet("*.mm")
public class MMBankController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SavingsAccountServiceImpl savingsAccountService;
	private RequestDispatcher dispatcher;
	boolean flag=false;
    public MMBankController() {
        super();
        try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/bankapp_db", "root", "root");
			PreparedStatement preparedStatement = 
					connection.prepareStatement("DELETE FROM ACCOUNT");
			preparedStatement.execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        savingsAccountService=new SavingsAccountServiceImpl();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path=request.getServletPath();
		PrintWriter out=response.getWriter();
		
		switch(path){
		case "/newSavingsAccount.mm":
			response.sendRedirect("OpenSavingsAccount.jsp");
			break;
			
		case "/openSavingsAccount.mm":
			String name=request.getParameter("txtAccHN");
			double amount=Double.parseDouble(request.getParameter("txtNumber"));
			boolean salary=request.getParameter("rdsal").equalsIgnoreCase("no")?false:true;
			try {
				savingsAccountService.createNewAccount(name,amount,salary);
				response.sendRedirect("getAll.mm");
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
			
		case "/closeSavingsAccount.mm":
			response.sendRedirect("CloseAccount.jsp");
			break;
			
		case "/closeAccount.mm":
			int accountID=Integer.parseInt(request.getParameter("closeaccount"));
			try {
				savingsAccountService.deleteAccount(accountID);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}	
			break;
			
		case "/currentBalanceSA.mm":
			response.sendRedirect("CurrentBalance.jsp");
			break;
			
		case "/currentBalance.mm":
			int accountNumber=Integer.parseInt(request.getParameter("getcurrentbalance"));
			try {
				double balance=savingsAccountService.checkCurrentBalance(accountNumber);
					out.println("Your CurrentBalance is:"+balance);
				} catch (AccountNotFoundException e) {
					e.printStackTrace();
				
				} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;
			
		case "/withDrawAmount.mm" :
			response.sendRedirect("WithDrawAmount.jsp");
			break;
			
		case "/withdraw.mm":
			int accountNumber1=Integer.parseInt(request.getParameter("withdrawaccnum"));
			double amount1=Double.parseDouble(request.getParameter("withdrawamount"));
			SavingsAccount savingsAccount1 = null;
			try {
				savingsAccount1 = savingsAccountService.getAccountById(accountNumber1);
				savingsAccountService.withdraw(savingsAccount1, amount1);
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
			response.sendRedirect("DepositAmount.jsp");
			break;
			
		case "/deposit.mm":
			int accountNumber2=Integer.parseInt(request.getParameter("depositaccnum"));
			double amount2=Double.parseDouble(request.getParameter("depositamount"));
			SavingsAccount savingsAccount2 = null;
			try {
				savingsAccount2 = savingsAccountService.getAccountById(accountNumber2);
				savingsAccountService.deposit(savingsAccount2, amount2);
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
			response.sendRedirect("FundTransfer.jsp");
			break;
			
		case "/transfer.mm":
			int senderAccountNumber=Integer.parseInt(request.getParameter("senderaccnum"));
			int receiverAccountNumber=Integer.parseInt(request.getParameter("receiveraccnum"));
			double amount3=Double.parseDouble(request.getParameter("amounttotransfer"));
			try {
				SavingsAccount senderSavingsAccount = savingsAccountService.getAccountById(senderAccountNumber);
				SavingsAccount receiverSavingsAccount = savingsAccountService.getAccountById(receiverAccountNumber);
				savingsAccountService.fundTransfer(senderSavingsAccount, receiverSavingsAccount, amount3);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "/getAll.mm":
			try {
				List<SavingsAccount> accounts = savingsAccountService.getAllSavingsAccount();
				request.setAttribute("accounts", accounts);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "/searchForm.mm":
			response.sendRedirect("SearchForm.jsp");
			break;
			
		case "/search.mm":
			int accountNumber4 = Integer.parseInt(request.getParameter("txtAccountNumber"));
			try {
				SavingsAccount account = savingsAccountService.getAccountById(accountNumber4);
				request.setAttribute("account", account);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException | AccountNotFoundException e) {
				e.printStackTrace();
			}
			break;
		
		case "/sortByName.mm":
			flag=!flag;
			ArrayList<SavingsAccount> accountName;
			try {
				accountName = (ArrayList<SavingsAccount>) savingsAccountService.getAllSavingsAccount();
				Collections.sort(accountName,new Comparator<SavingsAccount>()
				{
				@Override
				public int compare(SavingsAccount arg0, SavingsAccount arg1) {
					int result= arg0.getBankAccount().getAccountHolderName().compareTo(arg1.getBankAccount().getAccountHolderName());
					if(flag==true)
					{
						return result;
					}
					else
					{
						return -result;
					}
				}
				});
				request.setAttribute("accounts", accountName);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;	
				
		case "/sortByBalance.mm":
			flag=!flag;
			ArrayList<SavingsAccount> accountBalance;
			try {
				accountBalance = (ArrayList<SavingsAccount>) savingsAccountService.getAllSavingsAccount();
				Collections.sort(accountBalance,new Comparator<SavingsAccount>()
				{
				@Override
				public int compare(SavingsAccount arg0, SavingsAccount arg1) {
					int result= (int) (arg0.getBankAccount().getAccountBalance()- arg1.getBankAccount().getAccountBalance());
					if(flag==true)
					{
						return result;
					}
					else
					{
						return -result;
					}
				}
				});
				request.setAttribute("accounts", accountBalance);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;	
			
		case "/sortById.mm":
			flag=!flag;
			ArrayList<SavingsAccount> accountId;
			try {
				accountId = (ArrayList<SavingsAccount>) savingsAccountService.getAllSavingsAccount();
				Collections.sort(accountId,new Comparator<SavingsAccount>()
				{
				@Override
				public int compare(SavingsAccount arg0, SavingsAccount arg1) {
					int result= (int) (arg0.getBankAccount().getAccountNumber()- arg1.getBankAccount().getAccountNumber());
					if(flag==true)
					{
						return result;
					}
					else
					{
						return -result;
					}
				}
				});
				request.setAttribute("accounts", accountId);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			break;	
			
			
		case "/sortBySalaried.mm":
			flag=!flag;
			ArrayList<SavingsAccount> accountSalary;
			try {
				accountSalary = (ArrayList<SavingsAccount>) savingsAccountService.getAllSavingsAccount();
				Collections.sort(accountSalary,new Comparator<SavingsAccount>()
				{
				@Override
				public int compare(SavingsAccount arg0, SavingsAccount arg1) {
					int result= Boolean.compare(arg0.isSalary(), arg1.isSalary());
					if(flag==true)
					{
						return result;
					}
					else
					{
						return -result;
					}
				}
				});
				request.setAttribute("accounts", accountSalary);
				dispatcher = request.getRequestDispatcher("AccountDetails.jsp");
				dispatcher.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;	
			
		case "/update.mm":
	  
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
